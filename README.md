![title](extras/title.png)
[![GitHub issues](https://img.shields.io/github/issues/theapache64/topcorn)](https://github.com/theapache64/topcorn/issues)
[![GitHub forks](https://img.shields.io/github/forks/theapache64/topcorn)](https://github.com/theapache64/topcorn/network)
[![GitHub stars](https://img.shields.io/github/stars/theapache64/topcorn)](https://github.com/theapache64/topcorn/stargazers)
[![GitHub license](https://img.shields.io/github/license/theapache64/topcorn)](https://github.com/theapache64/topcorn/blob/master/LICENSE)
[![Twitter](https://img.shields.io/twitter/url?style=social)](https://twitter.com/intent/tweet?text=Wow:&url=https%3A%2F%2Fgithub.com%2Ftheapache64%2Ftopcorn)

![light_screenshots](extras/light.png)

# TopCorn 🍿

A minimalistic movie listing app to browse IMDB's top 250 movies,
built to *demonstrate MVVM with latest hot-trending Android development tools*.

## Dark Mode Available 🌙
![dark_screenshots](extras/dark.png)

## Download 📥

- Download latest APK from [here](https://github.com/theapache64/topcorn/releases/latest/download/app-debug.apk)

## Built With 🛠


- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dagger 2](https://dagger.dev/) - Dependency Injection Framework
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Glide](https://bumptech.github.io/glide/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [TwinKill](https://github.com/theapache64/twinkill) - A simple library, a collection of utility classes wrapped around JetPack components 
- [MaterialColors](https://github.com/theapache64/material_colors) - Android material color palettes

## Architecture 🗼

This project follows the famous MVVM architecture and best practices from Google's [GithubBrowserSample](https://github.com/android/architecture-components-samples/tree/master/GithubBrowserSample)

![](extras/arch.png)

## Project Structure 📂

```
.
├── App.kt
├── data
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── Converters.kt
│   │   ├── daos
│   │   │   └── MoviesDao.kt
│   │   └── entities
│   ├── remote
│   │   ├── ApiInterface.kt
│   │   └── Movie.kt
│   └── repositories
│       └── movies
│           └── MoviesRepo.kt
├── di
│   ├── components
│   │   └── AppComponent.kt
│   └── modules
│       ├── ActivitiesBuilderModule.kt
│       ├── AppModule.kt
│       ├── DatabaseModule.kt
│       ├── NetworkModule.kt
│       ├── RepoModule.kt
│       └── ViewModelModule.kt
├── models
│   └── FeedItem.kt
├── ui
│   ├── activities
│   │   ├── feed
│   │   │   ├── FeedActivity.kt
│   │   │   └── FeedViewModel.kt
│   │   ├── movie
│   │   │   ├── MovieActivity.kt
│   │   │   └── MovieViewModel.kt
│   │   ├── splash
│   │   │   ├── SplashActivity.kt
│   │   │   └── SplashViewModel.kt
│   └── adapters
│       ├── FeedAdapter.kt
│       └── MoviesAdapter.kt
└── utils
    ├── BindingAdapters.kt
    ├── NetworkBoundResource.kt
    ├── retrofit
    │   ├── FlowResourceCallAdapterFactory.kt
    │   └── FlowResourceCallAdapter.kt
    └── test
        ├── EspressoIdlingResource.kt
        └── OpenForTesting.kt

21 directories, 30 files
```

## Credits 🤗

- 🤓 Icons are from [flaticon.com](https://www.flaticon.com/) 
- 🖌️ Design inspired from [AnimeXStream](https://github.com/mukul500/AnimeXStream) 
- 💽 Data from [top250 API](https://github.com/theapache64/top250)
- 📄 Thanks [Foodium](https://github.com/patilshreyas/Foodium)

## TODO 🗒️

  - [x] Improve algorithms and code review 
  - [x] Add test cases
  - [ ] Integrate OMDB API to add search feature
  - [ ] Add favorites

## Author ✍️

- theapache64
