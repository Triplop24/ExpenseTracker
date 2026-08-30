# ExpenseTracker

**REST API для управления расходами.**

Учебный backend-проект для практики разработки приложений на **Java** и **Spring Boot** с использованием **PostgreSQL**.



## Стек технологий

* **Java 21**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL**
* **Gradle**
* **JUnit 5**



## Возможности

* Создание расхода
* Получение расхода по ID
* Обновление расхода
* Удаление расхода
* Поиск расхода по ID
* Фильтрация расходов по категории
* Поиск расходов по диапазону суммы
* Подсчёт общей суммы расходов по категории
* Валидация данных расхода
* Проверка на дублирование ID



## Требования

Перед запуском убедитесь, что установлены:

* **Java 21+**
* **PostgreSQL**
* **Gradle**



## Настройка базы данных

Приложение использует **PostgreSQL**.

Создайте базу данных:

```sql
CREATE DATABASE expense_tracker;
```

Настройки подключения находятся в `application.properties`.

Пароль базы данных **не хранится непосредственно в проекте**. Он передаётся через переменную окружения `DB_PASSWORD`.

Пример конфигурации:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_tracker
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

Перед запуском приложения необходимо установить переменную окружения:

```text
DB_PASSWORD=your_password
```
Для работы с API используйте соответствующие endpoints, описанные в разделе

## Тестирование

Для тестирования используются:

* **JUnit 5**
* **Spring Boot Test**

Тестами покрыта основная логика работы с расходами:

* создание;
* получение;
* обновление;
* удаление;
* поиск и фильтрация;
* валидация данных;
* подсчёт сумм по категориям.

**Примечание:** для запуска тестов требуется доступная PostgreSQL и настроенная переменная окружения `DB_PASSWORD`.


## API

### Создание расхода

```http
POST /expenses
```

### Получение расхода

```http
GET /expenses/{id}
```

### Обновление расхода

```http
PUT /expenses/{id}
```

### Удаление расхода

```http
DELETE /expenses/{id}
```

### Получение всех расходов

```http
GET /expenses
```

### Фильтрация по категории

```http
GET /expenses/total-expenses?category=FOOD
```

### Поиск по диапазону суммы

```http
GET /expenses/total-expenses?minAmount=800&maxAmount=1200
`````



## Цель проекта

Проект создан для практики:

* разработки **REST API**;
* работы с **Spring Boot**;
* работы с **Spring Data JPA и Hibernate**;
* взаимодействия с **PostgreSQL**;
* работы с Git и GitHub.
