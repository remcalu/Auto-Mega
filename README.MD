# Auto Mega - An automobile finder tool

## Author
Remus Calugarescu

## Last Major Modification
August 24, 2022

## Noteworthy implemented techologies
- Java
- Spring Boot
- React
- Redux
- Typescript
- Docker

## Purpose
Recently, I've been trying to buy a car, but I've noticed that scrolling through multiple websites daily like Autotrader, Carpages, and Kijiji with all their different characteristics and quirks was quite time consuming is annoying. So this is what inspired me to create Auto Mega, a tool that allows a user to search for vehicles through one centralized UI, and view ads in a standardized format.

I also wanted to get a high quality full stack

## Vehicle searching support
|                  | Autotrader | Carpages | Kijiji |
|------------------|------------|----------|--------|
| Toyota Corolla   | ✅          | ✅        | ✅      |
| Toyota Camry     | ✅          | ✅        | ✅      |
| Toyota Avalon    | ✅          | ✅        | ✅      |
| Toyota Matrix    | ✅          | ✅        | ✅      |
| Toyota Yaris     | ✅          | ✅        | ✅      |
| Honda Civic      | ✅          | ✅        | ✅      |
| Honda Accord     | ✅          | ✅        | ✅      |
| Honda Insight    | ✅          | ✅        | ✅      |
| Kia Forte        | ✅          | ✅        | ✅      |
| Kia Niro         | ✅          | ✅        | ✅      |
| Hyundai Elantra  | ✅          | ✅        | ✅      |
| Hyundai Accent   | ✅          | ✅        | ✅      |
| Hyundai Sonata   | ✅          | ✅        | ✅      |
| Hyundai Veloster | ✅          | ✅        | ✅      |
| Hyundai Venue    | ✅          | ✅        | ✅      |
| Mazda 3          | ✅          | ✅        | ✅      |
| Mazda 6          | ✅          | ✅        | ✅      |
| Nissan Altima    | ✅          | ✅        | ✅      |
| Nissan Maxima    | ✅          | ✅        | ✅      |
| Nissan Sentra    | ✅          | ✅        | ✅      |
| Nissan Versa     | ✅          | ✅        | ✅      |
| Lexus Is         | ✅          | ✅        | ✅      |
| Lexus Es         | ✅          | ✅        | ✅      |

## Gradle commands
~~~~
gradle backendRun     Builds and runs the Java and Spring Boot backend
gradle frontendRun    Builds and runs the React frontend
gradle buildAll       Builds the frontend and backend
gradle cleanAll       Cleans frontend and backend builds
~~~~
## Features and progress estimates

#### Todo
- Scraping for Kijiji Autos
- Email notifications
- Backend unit tests
- Frontend unit tests
- Host on AWS EC2
- Frontend validation
  
#### 25% done
- Reduce cognitive complexity
- Javadoc
- Backend validation

#### 50% done

#### 75% done
- Log4j

#### Completed
- Implement OOP practices
- Integrate Autotrader as a resource
- Integrate Kijiji as a resource
- Integrate Carpages as a resource
- Spring boot integration
- Implement MySQL
- Use React for frontend
- Use Typescript
- Use Redux
- Create gradle commands to run frontend
- Create functional docker image