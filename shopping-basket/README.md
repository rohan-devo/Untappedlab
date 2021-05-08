## Shopping Basket Application

------

### Steps to run the Application

- Setup OpenJDK (> 11).
- Navigate into the projects root folder
- Run `./mvnw clean install`
- Run `./mvnw spring-boot:run`

### Endpoints

#### Add Product to Basket

Types of Products: `FOOD`, `MEDICAL_PRODUCT`, `OTHER`

POST `/api/v1/baskets/products`

**Sample Payload:**

```json
[
  {
    "name": "1",
    "quantity": 1,
    "price": 17.85,
    "type": "OTHER",
    "isImported": false
  },
  {
    "name": "2",
    "quantity": 2,
    "price": 11.98,
    "type": "MEDICAL_PRODUCT",
    "isImported": false
  },
  {
    "name": "3",
    "quantity": 1,
    "price": 1.5,
    "type": "FOOD",
    "isImported": false
  }
]
```

**Response:** 200 OK

```json
{
  "basketId": "8090405b-d0b5-410c-99fd-1e5fd1a8fd3c"
}
```

GET `/api/v1/baskets/{{basketId}}/receipt`

**Response**:

```json
{
    "timestamp": "2021-05-07T15:03:47.210574Z",
    "orderItems": [
        {
            "name": "1",
            "quantity": 1,
            "price": 19.65,
            "tax": 1.78
        },
        {
            "name": "2",
            "quantity": 2,
            "price": 24.0,
            "tax": 0.0
        },
        {
            "name": "3",
            "quantity": 1,
            "price": 1.5,
            "tax": 0.0
        }
    ],
    "salesTaxes": 1.78,
    "total": 45.15
}
```