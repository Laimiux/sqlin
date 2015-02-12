package com.laimiux.sqlin

public abstract class Table<T>(val tableName: String) {
    abstract val columns: Array<out Column<out Any>>

    abstract val converter: DaoConverter<T>

    fun getColumnNames(): Array<String> {
        return columns.map({column -> column.getColumnName() }).copyToArray()
    }

    fun column<T : Any>(name: String, type: TypeProperty<T>, vararg properties: Property): Column<T> {
        return object : Column<T> {
            override fun getColumnName(): String {
                return name
            }

            override val type: TypeProperty<T> = type

            override fun getProperties(): Array<out Property> {
                return properties
            }
        }
    }
}