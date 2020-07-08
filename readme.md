# Debouncer

A simple way to debounce repeated calls.

## Install

Add this dependency on your build.gradle:

```groovy
// TODO: Publish it
```

## Usage

1. Create a new instance from `Debouncer`:

```kotlin
private val debouncer = Debouncer()
```

2. Launch the task to be debounced

```kotlin
// Example with a click on a button (Android)
button.setOnClickListener { showToast() }

// Execute the function with debounce
private fun showToast() = debouncer.launch(delayDuration = 1000) {
    Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_SHORT).show()
}
```

## Tips

### Reuse debouncer

If you have many actions that have to be debounced, you can reuse the same debouncer for all of them.
The `launch` function provides you a parameter called `context`. This will receive any kotlin value/object/instance.
A good way to handle these context, is to creating empty objects to them, like the default `DefaultDebounceContext`.

```kotlin
// Create the contexts
object FirstContext
object SecondContext

// Example with a click on a button (Android)
button.setOnClickListener { showHelloWorldToast() }
button.setOnClickListener { showHelloThereToast() }

// First Context
private fun showHelloWorldToast() = debouncer.launch(delayDuration = 1000, context = FirstContext) {
    Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_SHORT).show()
}

// Second Context
private fun showHelloThereToast() = debouncer.launch(delayDuration = 1000, context = SecondContext) {
    Toast.makeText(this@MainActivity, "Hello there", Toast.LENGTH_SHORT).show()
}
```

### Change coroutine scope

If you already have a coroutine scope and want to reuse it, you can pass it as a constructor parameter.
An example is using the lifecycleScope/viewModelScope from android jetpack lifecycle.
By default, each new `Debouncer` creates a new coroutine scope with `Dispatchers.Main` context.
For more details, check the `DebounceCoroutineScope`.

```kotlin
private val debouncer = Debouncer(lifecycleScope)
button.setOnClickListener { showToast() }

// This launch will use `lifecycleScope`
private fun showToast() = debouncer.launch(delayDuration = 1000) {
    Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_SHORT).show()
}
```

### Change coroutine context on launch

If you want to change the context the debounce will run, you can provide the `coroutineContext` to it.

```kotlin
// This launch will run on IO
private fun showToast() = debouncer.launch(delayDuration = 1000, coroutineContext = Dispatchers.IO) {
    Toast.makeText(this@MainActivity, "Hello world", Toast.LENGTH_SHORT).show()
}
```

## Next Steps

- [ ] Add KTLint
- [ ] Run unit tests on github actions
- [ ] Return the value from the call that did run

## License
    Copyright 2020 Gustavo Fão. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.