package your.org.myapp.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;

public class HelloWorldTask extends AbstractTask {

	private final CyNetworkFactory helloworldtaskfactory;
	private final CyNetworkManager helloworldtaskmanager;
	private final CyNetworkNaming  nameU;
	
	private final CyNetworkViewFactory networkView;
	private final CyNetworkViewManager networkViewManager;
	
	
		public HelloWorldTask (final CyNetworkManager helloworldtaskmanager, 
							   final CyNetworkNaming nameU, 
							   final CyNetworkFactory helloworldtaskfactory,
							   final CyNetworkViewFactory networkView,
                               final CyNetworkViewManager networkViewManager) {
			
      this.helloworldtaskmanager = helloworldtaskmanager;
	    this.nameU = nameU;
	    this.helloworldtaskfactory = helloworldtaskfactory;
	    this.networkViewManager=networkViewManager;	
	    this.networkView=networkView;
  }


	
		public void run(TaskMonitor monitor) throws Exception {
			
			// Create an empty network
				CyNetwork myNet = helloworldtaskfactory.createNetwork();
				myNet.getRow(myNet).set(CyNetwork.NAME, nameU.getSuggestedNetworkTitle("Hello World Network"));
		
		
			// Add two nodes to the network
				CyNode node1 = myNet.addNode();
				CyNode node2 = myNet.addNode();
				
			// set name for new nodes
			
				CyTable Table = myNet.getDefaultNodeTable(); // Added CyTable nodeTable = myNet.getDefaultNodeTable(); 
																//to get the node table for the network.
			
				myNet.getDefaultNodeTable().getRow(node1.getSUID()).set("name", "Node1");
				myNet.getDefaultNodeTable().getRow(node2.getSUID()).set("name", "Node2");	

			// To create two new node columns, you can use the following code
				Table.createColumn("Greeting", String.class, false);
				Table.createColumn("Value", Double.class, false);

			 //To add data to the new columns, you can use the following code
				
				CyRow row1 = Table.getRow(node1.getSUID());
				CyRow row2 = Table.getRow(node2.getSUID());

				row1.set("Greeting", new String("Hello"));    //creates a list of strings for the "Greeting" column. 
				row1.set("Value", 1.0);                          //and adds a double value to the "Value" column for both nodes.
			
				row2.set("Greeting", new String("Geia") );
				row2.set("Value", 2.0);

			// Add an edge
				myNet.addEdge(node1, node2, true);
			
			
			
			
				helloworldtaskmanager.addNetwork(myNet);
				
				
				final Collection<CyNetworkView> views = networkViewManager.getNetworkViews(myNet);
				
				CyNetworkView myView = null;
				if(views.size() != 0)
					myView = views.iterator().next();
				
				if (myView == null) {
					// create a new view for my network
					myView = networkView.createNetworkView(myNet);
					networkViewManager.addNetworkView(myView);
				} else {
					System.out.println("networkView already existed.");
				}
			
			// Set the variable destroyNetwork to true, the following code will destroy a network
				boolean destroyNetwork = false;
				if (destroyNetwork){

					// Destroy it
					helloworldtaskmanager.destroyNetwork(myNet);			
			
				}					
		}
}
