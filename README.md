# sqlin
Android sql database access library written in Kotlin


#### Usage In Kotlin
```[kotlin]
object AppDatabaseInfo: DatabaseInfo {
    override val databaseName: String = "sampledb.db"
    override val databaseVersion: Int = 1

    override fun getTables(): Array<out Table<out Any>> {
        return array()
    }
}
```
