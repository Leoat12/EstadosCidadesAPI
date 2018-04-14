from __future__ import print_function # Python 2/3 compatibility
import boto3
import json
import decimal

dynamodb = boto3.resource('dynamodb', region_name='us-east-2')

table = dynamodb.Table('EstadosCidades')

with open("estados-cidades.json", encoding="utf8") as json_file:
    estados = json.load(json_file)
    for estado in estados:
        sigla = estado['sigla']
        nome = estado['nome']
        cidades = estado['cidades']

        print("Adding states:", sigla, nome)

        table.put_item(
           Item={
               'sigla': sigla,
               'nome': nome,
               'cidades': cidades,
            }
        )