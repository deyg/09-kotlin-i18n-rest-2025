# language: pt
Funcionalidade: Saudação

  Cenário: saudação padrão
    Quando realizo uma requisição GET para "/api/saudacao?lang=pt_BR"
    Então o status deve ser 200
    E a mensagem deve ser "Olá, Dev!"
