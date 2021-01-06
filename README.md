# Pracownia programowania 5 milestone
![Java CI with Maven](https://github.com/2som/pp5/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

## Set up

Backend:

```
cd pp5

mvn install

mvn spring-boot:run
```


Client:
```
cd src/main/Client/

npm i
```

## Tests

```
mvn test
```

## API endopints


#### GET
```
    http://localhost:8080/api/products/

    http://localhost:8080/api/products/:id/

```

#### POST
```
    http://localhost:8080/api/products/
```

#### PATCH
```
    http://localhost:8080/api/products/:id/
```

#### DELETE
```
    http://localhost:8080/api/products/:id/
```
