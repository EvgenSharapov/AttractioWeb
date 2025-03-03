AddressController

GET
localhost:8080/api/address/all
localhost:8080/api/address/2f28e181-53eb-4a31-83f3-0e49f57f390e

POST
localhost:8080/api/address/create

{
"building": 133,
"street": "street",
"city": "Moscow",
"region": "Moscow reg"
}

PUT
localhost:8080/api/address/create/2f28e181-53eb-4a31-83f3-0e49f57f390e

{
"building": 122,
"street": "og street",
"city": "Moscow",
"region": "Moscow region"
}

DEL

localhost:8080/api/address/2f28e181-53eb-4a31-83f3-0e49f57f390e

-------------------------------------------

AttractionController

GET

localhost:8080/api/attraction/all
localhost:8080/api/attraction/44f4c1be-f304-4f5f-b642-e408dc9d2299
localhost:8080/api/attraction/city/Moscow
localhost:8080/api/attraction/region/Mosc
localhost:8080/api/attraction/service/Coffee
localhost:8080/api/attraction/name/Zo

POST

localhost:8080/api/attraction/create?addressId=2f28e181-53eb-4a31-83f3-0e49f57f390e

{
"name": "ZooZZ",
"description": "Зоопарк",
"type": "PARK"
}

PUT
localhost:8080/api/attraction/44f4c1be-f304-4f5f-b642-e408dc9d2299/2f28e181-53eb-4a31-83f3-0e49f57f390e

{
"name": "ZooZZa",
"description": "Зоопарк",
"type": "PARK"
}

DEL
localhost:8080/api/attraction/44f4c1be-f304-4f5f-b642-e408dc9d2299

-------------------------------------------

TravelServiceController

GET

localhost:8080/api/service/all

POST

localhost:8080/api/service/create

{
"name": "CoffeeBlack",
"description": "Кофе",
"type": "TOUR_GUIDE"
}

PUT
localhost:8080/api/service/124da179-8062-432d-8bc8-86b4b99cca6e

{
"name": "CoffeeBlackBlack",
"description": "Кофе",
"type": "TOUR_GUIDE"
}

DEL

localhost:8080/api/service/124da179-8062-432d-8bc8-86b4b99cca6e