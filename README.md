# EstadosCidadesAPI

Projeto de API para obter as cidades de um estado através de uma API simples implatanação e utilização.
A base de dados para estados e cidades foi retirada do seguinte link: https://gist.github.com/letanure/3012978 

Agradecimentos a **letanure** por disponibilizar este JSON. 

## Instruções de implantação

Este projeto consiste em uma função serverless para AWS Lambda que é utilizada para se conectar ao banco de dados onde estão armazenadas as informações sobre os estados e suas cidades. Este projeto foi criado para ser 100% serverless utilizando os recursos disponibilizados pela Amazon Web Services. Portanto, antes de abordar o passo a passo da implantação do projeto na AWS, alguns arquivos importantes devem ser descritos:

* **template.yml**: Este arquivo é utilizado pelo SAM CLI para realizar o empacotamento e implantação da função. O [SAM CLI](https://github.com/awslabs/aws-sam-local) é uma CLI utilizada para administrar templates [SAM (Serverless Application Model)](https://github.com/awslabs/serverless-application-model). SAM é um modelo desenvolvido pela AWS para facilitar a implantação de aplicações serverless utilizando os recursos do AWS CloudFormation. O arquivo *template.yml* é o arquivo que define o template SAM para a aplicação serverless. Mais detalhes sobre como o template é estruturado podem ser encontrados no repositório do SAM. 

* **swagger.yml**: SAM templates podem ser utilizados para definir não só funções Lambda, mas também tabelas do DynamoDB e APIs implantadas no API Gateway. Este arquivo define a API que irá agir como trigger da função Lambda implementada. A definição da API é feita utilizando Swagger padrão com extensões utilizadas pela AWS para configurar particularidades do API Gateway, informações sobre estas extensões podem ser encontradas [aqui](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-swagger-extensions.html). Este arquivo é referenciado pelo SAM template para criar a API automaticamente utilizando AWS CloudFormation. 

### Implantação

1. Instale SAM CLI utilizando o comando abaixo:

`npm install -g aws-sam-local`

2. Verifique sua instalação invocando SAM CLI:

`sam --version`

3. Realizar pequenas modificações nos arquivos *template.yml* e *swagger.yml* inserindo a região e o ID da conta no AWS no lugares apropriados. 

*template.yml (linha 29)*
`Resource: arn:aws:dynamodb:<region>:<account-id>:table/*`

*swagger.yml (linha 32)*
`uri: arn:aws:apigateway:<region>:lambda:path/2015-03-31/functions/arn:aws:lambda:<region>:<account-id>:function:EstadosAPIBackEndFunction/invocations`

4. Compile o projeto Java com a função Lambda utilizando Gradle. 

`gradle build` 

5. Execute o SAM CLI para empacotar os aterfatos necessários para a implantação da stack no CloudFormation. 

`sam package --template-file template.yml --s3-bucket <bucket-name> --output-template-file packaged.yaml`

Substitua `<bucket-name>` pelo nome de seu S3 bucket. Caso não possua um, crie um antes deste passo. 

6. Realize a implantação da stack usando SAM CLI e CloudFormation (O usuário da AWS precisa ter as permissões necessárias para criar todos os recursos da stack.)

`sam deploy --template-file ./packaged.yaml --stack-name <stack-name> --capabilities CAPABILITY_IAM` 


### Carregamento dos dados no DynamoDB

A stack implantada também cria a tabela no DynamoDB necessária para consultar os dados de estados e cidades. Porém, é necessário carregar os dados antes que a API fique 100% funcional. Para isto, na pasta load-data existe um script em Python que carrega os dados do arquivo JSON que o acompanha. Para executar o script tenha certeza que Python esteja instalado e execute os comandos abaixo:

`pip install boto3`

`cd load-data`

`python load_dynamo_db.py`

### Teste da API

A API pode ser testada utilizando console da AWS ou usando curl, Postman, etc.
