package com.example.breastcancer

import android.content.Context
import java.util.ArrayList
import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator


class ModelFacade private constructor(context: Context) {

    private var db: DB
    private val assetManager: AssetManager = context.assets
    private var fileSystem: FileAccessor

    private var currentBreastCancer: BreastCancerVO? = null
	private var currentBreastCancers: ArrayList<BreastCancerVO> = ArrayList()

    init {
    	//init
        db = DB(context, null)
        fileSystem = FileAccessor(context)
	}

    companion object {
        private var instance: ModelFacade? = null
        fun getInstance(context: Context): ModelFacade {
            return instance ?: ModelFacade(context)
        }
    }
    
    fun createBreastCancer(x: BreastCancerVO) { 
          db.createBreastCancer(x)
          currentBreastCancer = x
	}
		    
    fun setSelectedBreastCancer(x: BreastCancerVO) {
	      currentBreastCancer = x
	}
	    
    fun classifyBreastCancer(breastCancer: BreastCancer) : String {
	    var result : String
		lateinit var tflite : Interpreter
	    lateinit var tflitemodel : ByteBuffer
	
	    try{
		    tflitemodel = loadModelFile(assetManager, "cancer.tflite")
	    	tflite = Interpreter(tflitemodel) 
	    } catch(ex: Exception){
		  ex.printStackTrace()
	    }
	        
	    val inputVal: FloatArray = floatArrayOf(
	            ((breastCancer.age - 24) / (89 - 24)).toFloat(),
	            ((breastCancer.bmi - 18.37) / (38.5787585 - 18.37)).toFloat(),
	            ((breastCancer.glucose - 60) / (201 - 60)).toFloat(),
	            ((breastCancer.insulin - 2.432) / (58.46 - 2.432)).toFloat(),
	            ((breastCancer.homa - 4.311) / (90.28 - 4.311)).toFloat(),
	            ((breastCancer.leptin - 1.6502) / (38.4 - 1.6502)).toFloat(),
	            ((breastCancer.adiponectin - 3.21) / (82.1 - 3.21)).toFloat(),
	            ((breastCancer.resistin - 45.843) / (1698.44 - 45.843)).toFloat(),
	            ((breastCancer.mcp - 45.843) / (1698.44 - 45.843)).toFloat()
	        )
	    val outputVal: ByteBuffer = ByteBuffer.allocateDirect(8)
	    outputVal.order(ByteOrder.nativeOrder())
	    tflite.run(inputVal, outputVal)
	    outputVal.rewind()
	        
	  	val labelsList : List<String> = listOf ("positive","negative")
	    val output = FloatArray(2)
	        for (i in 0..1) {
	            output[i] = outputVal.float
	        }
	        
	    result = getSortedResult(output, labelsList)[0].toString()
	        
	        breastCancer.outcome = result
	        persistBreastCancer(breastCancer)
	        
	     return result
	    }
	    
    data class Recognition(
	     var id: String = "",
	     var title: String = "",
	     var confidence: Float = 0F
	     )  {
		override fun toString(): String {
		     return "$title ($confidence%)"
		}
	}
	    
	private fun getSortedResult(labelProbArray: FloatArray, labelList: List<String>): List<Recognition> {
	    
	       val pq = PriorityQueue(
	           labelList.size,
	           Comparator<Recognition> {
	                   (_, _, confidence1), (_, _, confidence2)
	                 -> confidence1.compareTo(confidence2) * -1
	           })
	    
	      for (i in labelList.indices) {
	           val confidence = labelProbArray[i]
	           pq.add(
	               Recognition("" + i,
	                   if (labelList.size > i) labelList[i] else "Unknown", confidence*100))
	            }
	           val recognitions = ArrayList<Recognition>()
	           val recognitionsSize = Math.min(pq.size, labelList.size)
	    
	           if (pq.isNotEmpty()) {
	               for (i in 0 until recognitionsSize) {
	                   recognitions.add(pq.poll())
	               }
	           }
	           else {
	               recognitions.add(Recognition("0", "Unknown",100F))
	           }
	           return recognitions
	}
	        	   
	private fun loadModelFile(assetManager: AssetManager, modelPath: String): ByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset, declaredLength)
    }


	fun listBreastCancer(): ArrayList<BreastCancerVO> {
        currentBreastCancers = db.listBreastCancer()
		
        return currentBreastCancers
	}

	fun listAllBreastCancer(): ArrayList<BreastCancer> {	
		currentBreastCancers = db.listBreastCancer()
		var res = ArrayList<BreastCancer>()
			for (x in currentBreastCancers.indices) {
					val vo: BreastCancerVO = currentBreastCancers[x]
				    val itemx = BreastCancer.createByPKBreastCancer(vo.getId())
	            itemx.id = vo.getId()
            itemx.age = vo.getAge()
            itemx.bmi = vo.getBmi()
            itemx.glucose = vo.getGlucose()
            itemx.insulin = vo.getInsulin()
            itemx.homa = vo.getHoma()
            itemx.leptin = vo.getLeptin()
            itemx.adiponectin = vo.getAdiponectin()
            itemx.resistin = vo.getResistin()
            itemx.mcp = vo.getMcp()
            itemx.outcome = vo.getOutcome()
			res.add(itemx)
		}
		return res
	}

    fun stringListBreastCancer(): ArrayList<String> {
        currentBreastCancers = db.listBreastCancer()
        val res: ArrayList<String> = ArrayList()
        for (x in currentBreastCancers.indices) {
            res.add(currentBreastCancers[x].toString())
        }
        return res
    }

    fun getBreastCancerByPK(value: String): BreastCancer? {
        val res: ArrayList<BreastCancerVO> = db.searchByBreastCancerid(value)
	        return if (res.isEmpty()) {
	            null
	        } else {
	            val vo: BreastCancerVO = res[0]
	            val itemx = BreastCancer.createByPKBreastCancer(value)
            itemx.id = vo.getId()
            itemx.age = vo.getAge()
            itemx.bmi = vo.getBmi()
            itemx.glucose = vo.getGlucose()
            itemx.insulin = vo.getInsulin()
            itemx.homa = vo.getHoma()
            itemx.leptin = vo.getLeptin()
            itemx.adiponectin = vo.getAdiponectin()
            itemx.resistin = vo.getResistin()
            itemx.mcp = vo.getMcp()
            itemx.outcome = vo.getOutcome()
	            itemx
	        }
    }
    
    fun retrieveBreastCancer(value: String): BreastCancer? {
        return getBreastCancerByPK(value)
    }

    fun allBreastCancerIds(): ArrayList<String> {
        currentBreastCancers = db.listBreastCancer()
        val res: ArrayList<String> = ArrayList()
            for (breastcancer in currentBreastCancers.indices) {
                res.add(currentBreastCancers[breastcancer].getId())
            }
        return res
    }

    fun setSelectedBreastCancer(i: Int) {
        if (i < currentBreastCancers.size) {
            currentBreastCancer = currentBreastCancers[i]
        }
    }

    fun getSelectedBreastCancer(): BreastCancerVO? {
        return currentBreastCancer
    }

    fun persistBreastCancer(x: BreastCancer) {
        val vo = BreastCancerVO(x)
        db.editBreastCancer(vo)
        currentBreastCancer = vo
    }
	

		
}
