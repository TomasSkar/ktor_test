package com.example

import com.example.models.Person
import com.mongodb.client.MongoClient
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

interface PersonService {
    fun create(person: Person): Id<Person>?
    fun findAll(): List<Person>
    fun findById(id: String): Person?
    fun findByName(name: String): List<Person>?
    fun updatePerson(personId: String, request: Person): Boolean
    fun deletePerson(id: String): Boolean
}

class PersonServiceImp(
    client: MongoClient
): PersonService {
    companion object {
        private const val PERSON_DB_NAME = "person"
    }
    //private val client = KMongo.createClient()
    private val database = client.getDatabase(PERSON_DB_NAME)
    private val personCollection = database.getCollection<Person>()

    override fun create(person: Person): Id<Person>? {
        personCollection.insertOne(person)
        return person.id
    }

    override fun findAll(): List<Person> = personCollection.find().toList()

    override fun findById(id: String): Person? {
        return try {
            val bsonId: Id<Person> = ObjectId(id).toId()
            personCollection.findOne(Person::id eq bsonId)
        } catch (e: Exception) {
            null
        }
    }

    override fun findByName(name: String): List<Person>? {
        return try {
            val caseInsensitiveTypeSafeFilter = (Person::name).regex(name, "i")
            // val caseSensitiveTypeSafeFilter = Person::name regex name
            // val nonTypeSafeFilter = "{name:{'\$regex' : '$name', '\$options' : 'i'}}"

            // Match the given name (case-sensitvie) and are older, than 40 years.
            // val withAndOperator = and(Person::name regex name, Person::age gt 40)
            /* val implicitAndOperator = personCollection.find(
                Person::name regex name, Person::age gt 40
            ) */
            // val withOrOperator = or(Person::name regex name, Person::age gt 40)

            val list = personCollection.find(caseInsensitiveTypeSafeFilter).toList()

            return list.ifEmpty { null }
        } catch (e: Exception) {
            null
        }
    }

    override fun updatePerson(personId: String, request: Person): Boolean {
        return findById(personId)?.let { person ->
            val updateResult = personCollection.replaceOne(
                replacement = person.copy(
                    name = request.name,
                    age = request.age
                )
            )
            updateResult.modifiedCount== 1L
        } ?: false
    }

    override fun deletePerson(id: String): Boolean {
        val deleteResult = personCollection.deleteOneById(ObjectId(id))
        return deleteResult.deletedCount == 1L
    }
}