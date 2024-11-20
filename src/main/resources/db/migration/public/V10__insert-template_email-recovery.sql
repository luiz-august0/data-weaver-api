INSERT INTO public.template_email (id, template_email, html)
SELECT nextval('gen_id_template_email'),
       'RECOVERY',
       '<!DOCTYPE html>
<html>
  <head>
    <title>Redefinição de Senha</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style>
      body,
      table,
      td,
      a {
        text-size-adjust: 100%;
      }
      img {
        border: 0;
        height: auto;
        display: block;
      }
      body {
        margin: 0;
        padding: 0;
        width: 100%;
        font-family: Arial, sans-serif;
        color: rgb(79, 79, 101);
        background-color: #f4f4f4;
      }
      .mainTable {
        width: 100%;
        background-color: #f4f4f4;
        padding: 40px 0;
      }
      .contentTable {
        background-color: #ffffff;
        max-width: 600px;
        width: 100%;
        margin: 0 auto;
        padding: 40px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      }
      .h1 {
        font-size: 36px;
        color: rgb(43, 38, 91);
        text-align: center;
        margin-bottom: 20px;
      }
      .Button-primary-wrapper {
        text-align: center;
        margin-top: 30px;
      }
      .Button-primary-wrapper a {
        color: #ffffff;
      }
      .Button-primary {
        background-color: rgb(43, 38, 91);
        color: #ffffff;
        padding: 18px;
        text-decoration: none;
        display: block;
        width: calc(100% - 40px);
        max-width: 560px;
        margin: 0 auto;
        font-size: 18px;
        border-radius: 8px;
      }
      .text {
        font-size: 16px;
        line-height: 1.5;
        margin-bottom: 20px;
        text-align: left;
      }
      .footer {
        text-align: center;
        font-size: 14px;
        margin-top: 40px;
        color: rgb(100, 100, 100);
      }
    </style>
  </head>

  <body>
    <table class="mainTable">
      <tr>
        <td align="center">
          <table class="contentTable">
            <tr>
              <td align="center">
                <h1 class="h1">Redefina sua senha</h1>
              </td>
            </tr>
            <tr>
              <td class="text">
                <p>Perdeu o acesso à sua senha? Não se preocupe, o Data Weaver está aqui para ajudar!</p>
                <p>Clique no botão abaixo para criar uma nova senha e retomar o acesso à sua conta:</p>
              </td>
            </tr>
            <tr>
              <td class="Button-primary-wrapper">
                <a href="::url_redirect_signature::" class="Button-primary">Criar nova senha</a>
              </td>
            </tr>
            <tr>
              <td class="footer">
                <p>Este e-mail foi enviado pelo sistema Data Weaver</p>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
'
WHERE NOT EXISTS (SELECT 1
                  FROM public.template_email
                  WHERE template_email = 'RECOVERY');
