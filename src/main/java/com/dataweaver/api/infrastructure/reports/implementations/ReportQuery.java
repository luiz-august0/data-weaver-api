package com.dataweaver.api.infrastructure.reports.implementations;

import com.dataweaver.api.infrastructure.reports.interfaces.IReportColumn;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportFilter;
import com.dataweaver.api.infrastructure.reports.interfaces.IReportQuery;
import com.dataweaver.api.utils.ListUtil;
import com.dataweaver.api.utils.NumericUtil;
import com.dataweaver.api.utils.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReportQuery implements IReportQuery {

    private final EntityManager entityManager;

    @Override
    public Page<Map<String, Object>> getQueryResult(String sql, Pageable pageable, Map<IReportFilter, Object> filters, List<? extends IReportColumn> reportColumns) {
        NativeQuery<Tuple> query = buildQuery(sql, filters, pageable);
        List<Map<String, Object>> resultMapList = new ArrayList<>();

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Tuple> result = query.getResultList();

        result.forEach(row -> {
            Map<String, Object> mapResult = new HashMap<>();
            List<TupleElement<?>> tupleElements = row.getElements();

            reportColumns.forEach(reportColumn -> {
                Object value = row.get(reportColumn.getField());

                Object formattedValue = Utils.isNotEmpty(value) ? reportColumn.getFormat().getColumnFormatter().formatValue(value) : null;

                mapResult.putIfAbsent(reportColumn.getField(), formattedValue);

                tupleElements.removeIf(tupleElement -> tupleElement.getAlias().equals(reportColumn.getField()));
            });

            tupleElements.forEach(tupleElement -> {
                mapResult.putIfAbsent(tupleElement.getAlias(), row.get(tupleElement));
            });

            resultMapList.add(mapResult);
        });

        return PageableExecutionUtils.getPage(resultMapList, pageable, () -> NumericUtil.parseInt(buildQueryCount(sql, filters).getSingleResult().get(0)));
    }

    @Override
    public Map<String, Object> getTotalizersQueryResult(String sql, Map<IReportFilter, Object> filters) {
        NativeQuery<Tuple> query = buildQueryTotalizers(sql, filters);

        return ListUtil.tupleToMap(query.getSingleResult());
    }

    @SuppressWarnings("unchecked")
    private NativeQuery<Tuple> buildQuery(String sql, Map<IReportFilter, Object> filters, Pageable pageable, Boolean count) {
        String filtersSql = filters.keySet().stream().map(iReportFilter -> " " + iReportFilter.getSql() + " ").toList().stream().reduce(String::concat).orElse("");
        sql = sql.replace(":filters", filtersSql);

        if (count) {
            sql = " select count(*) from ( " + sql + " ) as tmp ";
        }

        if (Utils.isNotEmpty(pageable) && !count) {
            Sort sort = pageable.getSort();

            if (sort.isSorted()) {
                Sort.Order order = sort.get().findFirst().orElse(null);

                if (Utils.isNotEmpty(order)) {
                    int indexOfOrderBy = sql.lastIndexOf("order by");
                    String orderBy = " order by " + order.getProperty() + " " + order.getDirection() + " ";

                    if (indexOfOrderBy != -1) {
                        sql = sql.replace(sql.substring(indexOfOrderBy), orderBy);
                    } else {
                        sql = sql + orderBy;
                    }
                }
            }
        }

        NativeQuery<Tuple> query = (NativeQuery<Tuple>) entityManager.createNativeQuery(sql, Tuple.class);

        filters.forEach((iReportFilter, filterValue) -> {
            query.setParameter(iReportFilter.getParameter(), filterValue);
        });

        return query;
    }

    private NativeQuery<Tuple> buildQueryTotalizers(String sql, Map<IReportFilter, Object> filters) {
        return buildQuery(sql, filters, null, false);
    }

    private NativeQuery<Tuple> buildQueryCount(String sql, Map<IReportFilter, Object> filters) {
        return buildQuery(sql, filters, null, true);
    }

    private NativeQuery<Tuple> buildQuery(String sql, Map<IReportFilter, Object> filters, Pageable pageable) {
        return buildQuery(sql, filters, pageable, false);
    }

}
