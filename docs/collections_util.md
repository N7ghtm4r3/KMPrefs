**KMPrefs** provides utility methods to work with locally stored collections, making them easier to access and use outside the storage layer.
These methods work whether the collection data is sensitive or not

## Add an element

Using the `addToCollection` method you can add a new element to a local stored collection

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// create the instance to store
val collection = mutableListOf("hello")

// after the collection storage 
kmPrefs.addToCollection(
    element = "world",
    key = "your_key"
)

// [hello, world]
```

## Remove an element

Using the `removeFromCollection` method you can remove an element from a local stored collection

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// after the collection storage 
kmPrefs.removeFromCollection(
    element = "world",
    key = "your_key"
)

// [hello]
```

## Add multiple elements

Using the `addAllToCollection` method you can add a new multiple elements to a local stored collection

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// after the collection storage 
kmPrefs.addAllToCollection(
    elements = setOf("world", "!"),
    key = "your_key"
)

// [hello, world, !]
```

## Remove multiple elements

Using the `removeAllFromCollection` method you can remove multiple elements from a local stored collection

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

// after the collection storage 
kmPrefs.removeAllFromCollection(
    elements = setOf("world", "!"),
    key = "your_key"
)

// [hello]
```

## General purpose method

The `useMutableCollection` method allows to work with a `MutableCollection` locally stored performing a custom usage

```kotlin
val kmPrefs = KMPrefs("your_storage_path") // create an instance

kmPrefs.useMutableCollection<String>(
    key = "your_key",
    usage = { localCollection ->
        println(localCollection.size)
    }
)

// 1
```

!!! Note

    This method is public due to the incompatibility between inline functions and private methods. However, its use is
    recommended only when strictly necessary because of the overhead involved in retrieving and restoring the collection 
    after custom usage