# ForYourself
# A program written entirely in Kotlin using the principles of Clean Architecture according to the MVVM pattern.
application for online sales. Basic functions include registration, personal account, search and product catalog applications.
Round assortment with catalogue. Mobile point of sale. The application is a showcase and an online cash register in one.
Catalog search. It is convenient when you can easily and quickly find the right product in the application, there is a full-text search.
## Splash and Login Screens
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.39.31.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.39.31.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.43.00.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.43.00.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.43.12.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.43.12.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.43.22.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.43.22.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.40.36.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.40.36.png)
## Main and Details Screens
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.38.19.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.38.19.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.38.28.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.38.28.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.38.04.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.38.04.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.37.49.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.37.49.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.37.38.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.37.38.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.37.25.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.37.25.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.37.02.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.37.02.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.37.10.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.37.10.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.36.49.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.36.49.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.36.27.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.36.27.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.36.09.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.36.09.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.35.48.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.35.48.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.35.35.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.35.35.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.35.18.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.35.18.png
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.34.44.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.34.44.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.34.18.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.34.18.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.33.50.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.33.50.png)
[<img src="meta/android/screenshots/Скриншот 20-06-2022 22.39.08.png" width=160>](meta/android/screenshots/Скриншот 20-06-2022 22.39.08.png)

## Libraries
### Android Jetpack
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) View binding is a
  feature that makes it easier for you to write code that interacts with views.
* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) An interface
  that automatically responds to lifecycle events.
* [Navigation](https://developer.android.com/guide/navigation?gclsrc=aw.ds&gclid=Cj0KCQiA09eQBhCxARIsAAYRiymyM6hTEs0cGr5ZCXOWtLhVUwDK1O86vf8V_Uq2DWvVYNFZwPFznzAaAllMEALw_wcB)
  Navigation refers to interactions that allow users to navigate through , enter, and exit various
  parts of the content in your app. Navigation component Android Jetpack helps you navigate, from
  simple button clicks to more complex templates like application panels and navigation bar. The
  navigation component also provides a consistent and predictable user interface, adhering to an
  established set of principles.
* [Room](https://developer.android.com/jetpack/androidx/releases/room) The Room persistence library
  provides an abstraction layer over SQLite to allow for more robust database access while
  harnessing the full power of SQLite.
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) Data objects that
  notify views of changes to the underlying database.
* Kotlin flows (https://developer.android.com/kotlin/flow) In coroutines, a flow is a type that can
  emit multiple values sequentially, as opposed to suspend functions that return only a single
  value. For example, you can use a flow to receive live updates from a database.
* ViewModel (https://developer.android.com/topic/libraries/architecture/viewmodel) Data related to
  the user interface that is not destroyed when the application is rotated. Easily schedule
  asynchronous tasks for optimal execution.
### DI
* Hilt (https://developer.android.com/training/dependency-injection/hilt-android) Hilt is a
  dependency injection library for Android that reduces the execution time of manual dependency
  injection into your project. Performing manual dependency injection requires that you create each
  class and its dependencies manually, and use containers to reuse and manage dependencies.
### Image
* [Glide](https://github.com/bumptech/glide) Glide is a fast and efficient open source media
  management and image loading framework for Android that wraps media decoding, memory and disk
  caching, and resource pooling into a simple and easy to use interface.
### Network
* [Retrofit2](https://github.com/square/retrofit) Type-safe HTTP client for Android and Java.
* [OkHttp](https://github.com/square/okhttp) HTTP + HTTP/2 client for Android and Java applications.
* [Parse-SDK-Android](https://github.com/parse-community/Parse-SDK-Android) A library that gives you
  access to the powerful Parse Server backend from your Android app. For more information about
  Parse and its features, see the website, getting started, and blog.
### Coroutines
* [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) Coroutines is a rich library for
  coroutines developed by JetBrains. It contains a number of high-level primitives with support for
  coroutines, which are discussed in this guide, including startup, asynchrony, and others.
### GitHub Custom Libraries
* [Circle Image View](https://github.com/hdodenhof/CircleImageView) A fast circular ImageView
  perfect for profile images. This is based on RoundedImageView from Vince Mi which itself is based
  on techniques recommended by Romain Guy.
* [Circular Progress View](https://github.com/VaibhavLakhera/Circular-Progress-View) A customisable
  circular progress view for android.
* [Push Down Animation Click](https://github.com/nontravis/pushdown-anim-click) A library for
  Android developers who want to create "push down animation click" for view like spotify
  application. :)

* [Shimmer] Shimmer (https://github.com/facebook/shimmer-android) Shimmer is an Android library that provides
  an easy way to add a shimmer effect to any view in your Android app.