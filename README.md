# RickandMorty

Android app built with Jetpack Compose that lets you search Rick and Morty characters and view character details. The project follows MVI with Clean Architecture, uses Hilt for dependency injection, Retrofit for networking, and Kotlin Flow for reactive streams.

## Features

Search characters by name  
Filter by status (Alive, Dead, Unknown)  
Character detail screen  
Auto search with debounce  
Error handling and retry  
Unit tests for use case and view model

## Tech Stack

Kotlin  
Jetpack Compose (Material 3)  
Navigation Compose  
Hilt  
Retrofit and OkHttp  
Kotlin Coroutines and Flow  
Coil  


## API

Base URL  
https://rickandmortyapi.com/


## Architecture

The project uses Clean Architecture with three layers.

Data layer  
Handles remote API calls, DTOs, and repository implementations.

Domain layer  
Contains models, repository interfaces, and use cases.

Presentation layer  
Contains UI (Compose), MVI state management, and view models.


