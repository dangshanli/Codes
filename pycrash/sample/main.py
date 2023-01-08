import requests

response = requests.get('http://httpbin.org/ip')
print('your ip is {0}'.format(response.json()['origin']))
