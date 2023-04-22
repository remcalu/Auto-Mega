# Auto Mega - A vehicle finder tool

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
Recently, I've been trying to buy a car, but I've noticed that scrolling through multiple websites daily like Autotrader, Carpages, and Kijiji with all their different characteristics and quirks was quite time consuming is annoying. So this is what inspired me to create Auto Mega, **a tool that allows a user to search for vehicles through one centralized UI, and view ads from various other websites in a standardized format**.

I did have a [previous project](https://github.com/remcalu/Autotrader-Scraper) that displayed vehicles found in a text file, but I decided to refactor it to use Java from Python, since it was quite slow at times, and also since I wanted to get some **practice with refactoring code from one language to another**.

I also wanted to get a **high quality full stack project** out since I've gained considerable skills for both frontend and backend development throughout school, and even more through my internships!

## Website Demo (January 2023)
https://www.youtube.com/watch?v=4qmC1L6LtFA&ab_channel=RemusCalugarescu
![Screenshot_2](https://user-images.githubusercontent.com/59599955/233768443-5fc2a82c-cafc-4792-8afc-6eba86d5f78a.png)

## Vehicle searching support
|                  | Autotrader  | Carpages   | Kijiji  | Image |
|------------------|-------------|------------|---------|-------|
| Toyota Corolla   | ✅          | ✅        | ✅      | ✅   |
| Toyota Camry     | ✅          | ✅        | ✅      | ✅   |
| Toyota Avalon    | ✅          | ✅        | ✅      | ✅   |
| Toyota Matrix    | ✅          | ✅        | ✅      | ✅   |
| Toyota Yaris     | ✅          | ✅        | ✅      | ✅   |
| Honda Civic      | ✅          | ✅        | ✅      | ✅   |
| Honda Accord     | ✅          | ✅        | ✅      | ✅   |
| Honda Insight    | ✅          | ✅        | ✅      | ✅   |
| Kia Forte        | ✅          | ✅        | ✅      | ✅   |
| Kia Niro         | ✅          | ✅        | ✅      | ✅   |
| Hyundai Elantra  | ✅          | ✅        | ✅      | ✅   |
| Hyundai Accent   | ✅          | ✅        | ✅      | ✅   |
| Hyundai Sonata   | ✅          | ✅        | ✅      | ✅   |
| Hyundai Veloster | ✅          | ✅        | ✅      | ✅   |
| Hyundai Venue    | ✅          | ✅        | ✅      | ✅   |
| Mazda 3          | ✅          | ✅        | ✅      | ✅   |
| Mazda 6          | ✅          | ✅        | ✅      | ✅   |
| Nissan Altima    | ✅          | ✅        | ✅      | ✅   |
| Nissan Maxima    | ✅          | ✅        | ✅      | ✅   |
| Nissan Sentra    | ✅          | ✅        | ✅      | ✅   |
| Nissan Versa     | ✅          | ✅        | ✅      | ✅   |
| Lexus Is         | ✅          | ✅        | ✅      | ✅   |
| Lexus Es         | ✅          | ✅        | ✅      | ✅   |

## Gradle commands
~~~~
$gradle backendRun            Builds and runs the Java and Spring Boot backend
$gradle frontendRun           Builds and runs the React frontend
$gradle frontendStop          Stops the frontend if exited through CTRL+C
$gradle buildAll              Builds the frontend and backend
$gradle cleanAll              Cleans frontend and backend builds
~~~~

## Features and progress estimates
| Todo                                 | 25% Done                    | 50% Done | 75% Done | Completed                              |
|--------------------------------------|-----------------------------|----------|----------|----------------------------------------|
| Integrate Kijiji Autos as a resource | Reduce cognitive complexity |          | Log4j    | Implement OOP practices                |
| Email notifications                  | Javadoc                     |          |          | Integrate Autotrader as a resource     |
| Backend unit tests                   | Backend validation          |          |          | Integrate Kijiji as a resource         |
| Frontend unit tests                  |                             |          |          | Spring boot integration                |
| Frontend validation                  |                             |          |          | Implement MySQL                        |
|                                      |                             |          |          | Use React for frontend                 |
|                                      |                             |          |          | Use Typescript                         |
|                                      |                             |          |          | Use Redux                              |
|                                      |                             |          |          | Create gradle commands to run frontend |
|                                      |                             |          |          | Create functional docker image         |
