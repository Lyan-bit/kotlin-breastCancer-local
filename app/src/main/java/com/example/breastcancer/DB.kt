package com.example.breastcancer

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, databaseName, factory, databaseVersion) {

    companion object{
        lateinit var database: SQLiteDatabase

        private val databaseName = "breastcancerApp.db"
        private val databaseVersion = 1

        const val breastCancerTableName = "BreastCancer"
        const val breastcancerColTableId = 0
        const val breastCancerColId = 1
        const val breastCancerColAge = 2
        const val breastCancerColBmi = 3
        const val breastCancerColGlucose = 4
        const val breastCancerColInsulin = 5
        const val breastCancerColHoma = 6
        const val breastCancerColLeptin = 7
        const val breastCancerColAdiponectin = 8
        const val breastCancerColResistin = 9
        const val breastCancerColMcp = 10
        const val breastCancerColOutcome = 11

        val breastcancerCols: Array<String> = arrayOf<String>("tableId", "id", "age", "bmi", "glucose", "insulin", "homa", "leptin", "adiponectin", "resistin", "mcp", "outcome")
        const val breastcancerNumberCols = 11
    
   }
private val breastcancerCreateSchema =
    "create table BreastCancer (tableId integer primary key autoincrement" +
        ", id VARCHAR(50) not null"+
        ", age integer not null"+
        ", bmi float not null"+
        ", glucose float not null"+
        ", insulin float not null"+
        ", homa float not null"+
        ", leptin float not null"+
        ", adiponectin float not null"+
        ", resistin float not null"+
        ", mcp float not null"+
        ", outcome VARCHAR(50) not null"+
    ")"

    override fun onCreate(db: SQLiteDatabase) {
         db.execSQL(breastcancerCreateSchema)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + breastcancerCreateSchema)
        onCreate(db)
    }

    fun onDestroy() {
        database.close()
    }

    fun listBreastCancer(): ArrayList<BreastCancerVO> {
        val res = ArrayList<BreastCancerVO>()
        database = readableDatabase
        val cursor: Cursor =
            database.query(breastCancerTableName, breastcancerCols, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast()) {
			res.add(setData(cursor))
            cursor.moveToNext()
        }
        cursor.close()
        return res
    }

    fun createBreastCancer(breastcancervo: BreastCancerVO) {
        database = writableDatabase
        database.insert(breastCancerTableName, breastcancerCols[1], putData(breastcancervo))
    }

    fun searchByBreastCancerid(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where id = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerage(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where age = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerbmi(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where bmi = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerglucose(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where glucose = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerinsulin(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where insulin = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerhoma(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where homa = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerleptin(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where leptin = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCanceradiponectin(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where adiponectin = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancerresistin(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where resistin = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCancermcp(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where mcp = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
				res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     
    fun searchByBreastCanceroutcome(value: String): ArrayList<BreastCancerVO> {
            val res = ArrayList<BreastCancerVO>()
	        database = readableDatabase
	        val args = arrayOf(value)
	        val cursor: Cursor = database.rawQuery(
	            "select tableId, id, age, bmi, glucose, insulin, homa, leptin, adiponectin, resistin, mcp, outcome from BreastCancer where outcome = ?",
	            args
	        )
	        cursor.moveToFirst()
	        while (!cursor.isAfterLast()) {
	            res.add(setData(cursor))
	            cursor.moveToNext()
	        }
	        cursor.close()
	        return res
	    }
	     

    fun editBreastCancer(breastcancervo: BreastCancerVO) {
        database = writableDatabase
        val args = arrayOf(breastcancervo.id)
        database.update(breastCancerTableName, putData(breastcancervo), "id =?", args)
    }

    fun deleteBreastCancer(value: String) {
        database = writableDatabase
        val args = arrayOf(value)
        database.delete(breastCancerTableName, "id = ?", args)
    }

	private fun setData(cursor: Cursor): BreastCancerVO {
		val breastcancervo = BreastCancerVO()
		breastcancervo.id = cursor.getString(breastCancerColId)
		breastcancervo.age = cursor.getInt(breastCancerColAge)
		breastcancervo.bmi = cursor.getFloat(breastCancerColBmi)
		breastcancervo.glucose = cursor.getFloat(breastCancerColGlucose)
		breastcancervo.insulin = cursor.getFloat(breastCancerColInsulin)
		breastcancervo.homa = cursor.getFloat(breastCancerColHoma)
		breastcancervo.leptin = cursor.getFloat(breastCancerColLeptin)
		breastcancervo.adiponectin = cursor.getFloat(breastCancerColAdiponectin)
		breastcancervo.resistin = cursor.getFloat(breastCancerColResistin)
		breastcancervo.mcp = cursor.getFloat(breastCancerColMcp)
		breastcancervo.outcome = cursor.getString(breastCancerColOutcome)

		return breastcancervo
	}

	private fun putData(breastcancervo: BreastCancerVO): ContentValues {
		val wr = ContentValues(breastcancerNumberCols)
		wr.put(breastcancerCols[breastCancerColId], breastcancervo.id)
		wr.put(breastcancerCols[breastCancerColAge], breastcancervo.age)
		wr.put(breastcancerCols[breastCancerColBmi], breastcancervo.bmi)
		wr.put(breastcancerCols[breastCancerColGlucose], breastcancervo.glucose)
		wr.put(breastcancerCols[breastCancerColInsulin], breastcancervo.insulin)
		wr.put(breastcancerCols[breastCancerColHoma], breastcancervo.homa)
		wr.put(breastcancerCols[breastCancerColLeptin], breastcancervo.leptin)
		wr.put(breastcancerCols[breastCancerColAdiponectin], breastcancervo.adiponectin)
		wr.put(breastcancerCols[breastCancerColResistin], breastcancervo.resistin)
		wr.put(breastcancerCols[breastCancerColMcp], breastcancervo.mcp)
		wr.put(breastcancerCols[breastCancerColOutcome], breastcancervo.outcome)
		return wr
	}

}
