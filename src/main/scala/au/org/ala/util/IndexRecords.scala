
package au.org.ala.util
import java.util.ArrayList
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import au.org.ala.biocache._

/**
 * Index the Cassandra Records to conform to the fields
 * as defined in the schema.xml file.
 *
 *
 *
 *@author Natasha Carter
 *
 */
object IndexRecords {

  val logger = LoggerFactory.getLogger("IndexRecords")
  var indexer = SolrOccurrenceDAO

  def main(args: Array[String]): Unit = {
    
    //delete the content of the index
    indexer.emptyIndex
   processMap
    //index any remaining items before exiting
//    indexer.index(items)
    indexer.finaliseIndex
    exit(0)
  }
  
  def processMap()={
    var counter = 0
    var startTime = System.currentTimeMillis
    var finishTime = System.currentTimeMillis
    var items = new ArrayList[OccurrenceIndex]()
    DAO.persistentManager.pageOverAll("occ", (guid, map)=> {
        counter += 1

        indexer.indexFromMap(guid, map)
         
        if (counter % 1000 == 0) {
          finishTime = System.currentTimeMillis
          logger.info(counter + " >> Last key : " + guid + ", records per sec: " + 1000f / (((finishTime - startTime).toFloat) / 1000f))
          startTime = System.currentTimeMillis

        }
        
        true
    })
  }

  def processFullRecords()={
    var counter = 0
    var startTime = System.currentTimeMillis
    var finishTime = System.currentTimeMillis
    var items = new ArrayList[OccurrenceIndex]()

     //page over all records and process
    OccurrenceDAO.pageOverAllVersions(versions => {
      counter += 1
      if (!versions.isEmpty) {
    	val v = versions.get
    	items.add(indexer.getOccIndexModel(v).get);
        //debug counter
        if (counter % 1000 == 0) {
          //add the items to the configured indexer
          indexer.index(items);
          items.removeAll(items);
          finishTime = System.currentTimeMillis
          logger.info(counter + " >> Last key : " + v(0).uuid + ", records per sec: " + 1000f / (((finishTime - startTime).toFloat) / 1000f))
          startTime = System.currentTimeMillis
        }
      }
      true

    })
  }

  

}
