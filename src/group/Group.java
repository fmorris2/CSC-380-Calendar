package group;

import java.util.ArrayList;
import java.util.HashMap;

import task.Task;
import user.Permissions;
import user.User;

public class Group {
   
	//Variables
    
	String groupName;
    String groupDescription;
    ArrayList<Task> groupTasks;
    ArrayList<User> groupMembers;
    User groupLeader;
    HashMap<User, Permissions> permissions;
    
    
    //Constructors
    
    /**
     * The parameterized constructor for the group class.
     * @param leader
     * @param name
     * @param description 
     */
    public Group(User leader, String name, String description){
        this.groupDescription = description;
        this.groupName = name;
        this.groupLeader = leader;
        this.groupMembers = new ArrayList<>();
        this.permissions = new HashMap<User, Permissions>();
        addUser(this.groupLeader, true, true, true);
        this.groupTasks = new ArrayList<Task>();
        
    }
  
    /**
    * A default constructor for testing the group class.
    */
    public Group(){
        this.groupDescription = "Group Description";
        this.groupName = "Test Group";
        this.groupTasks = new ArrayList<Task>();
        this.groupMembers = new ArrayList<User>();
        Task a = new Task(); Task b = new Task();
        User gm = new User();
        this.groupLeader = gm;
        this.permissions = new HashMap<User, Permissions>();
        addUser(this.groupLeader, true, true, true);
        addGroupTask(this.groupLeader, a); addGroupTask(this.groupLeader, b);
        
        

        
        
    }
    
    
    //Adding new tasks to the group
    
    /**
     * Adds a new task to the group task list
     * @param task
     */
    public void addGroupTask(User user, Task task) {
		//Need to assign task to a user
    	if(permissions.get(user).getCanCreateTasks()){
    		this.groupTasks.add(task);
//    		for (User i : this.groupMembers){
//    			if (this.permissions.get(i).getCanCompleteTasks()){
//    				i.addNewTask(task);
//    			}
//    		}
    	}
    	
	}
    
    //Removing task from the group
    
    /**
     * Removes a from the group task list
     * @param task
     */
    public void removeGroupTask(Task task) {
    	//Check permissions of user
    	//Interact with tasks of user
    	this.groupTasks.remove(task);
    }

	//Adding Users to Group
    
    
    /**
     * Method adds a new user to the group.
     * @param user
     * @param change
     * @param create
     * @param complete 
     */
    public void addUser(User user, Boolean change, Boolean create, Boolean complete) {
        //Check if user is a group leader
    	this.groupMembers.add(user);
        addPermissions(user, change, create, complete);
        user.addGroup(this);
    }
    
   
    private void addPermissions(User user, Boolean change, Boolean create, Boolean complete) {
    	Permissions newPerm = new Permissions(change, create, complete);
    	permissions.put(user, newPerm);
		
	}

	//Removing user from the group
    public void removeUser(User user) {
    	//Check for group member
    	this.groupMembers.remove(user);
    	this.permissions.remove(user);
    	user.removeGroup(this);
    	
    }
    
    //Changing Permissions
    
    
    /**
     * Changes permissions, and returns a new copy of the permissions list with changes.
     * @param user
     * @param change
     * @param create
     * @param complete
     * @return
     */
    public void setPermissions(User user, boolean change, boolean create, boolean complete) {
    	//Needs to check whether person is a group leader
    	
//    	if (this.permissions.containsKey(user)){
    	permissions.get(user).setCanChangeTasks(change);
        permissions.get(user).setCanCompleteTasks(complete);
        permissions.get(user).setCanCreateTasks(create);
//        } else {
        	
//        }
    }
    
    
    
    
    
    
    //Other getters and setters
    
    
    
    public String getGroupName(){ return groupName;}
    public void setGroupName(String groupName) { groupName = this.groupName;}
    public String getGroupDescription(){ return groupDescription;}
    public void setGroupDescription(String groupDescription){ groupDescription = this.groupDescription;}
    public ArrayList<Task> getGroupTasks() {return groupTasks;}
    public void setGroupTasks(ArrayList<Task> groupTasks){groupTasks = this.groupTasks;}
    public ArrayList<User> getGroupMembers() {return groupMembers;}
    public void setGroupMembers(ArrayList<User> groupMembers){groupMembers = this.groupMembers;}
    public User getGroupLeader(){return groupLeader;}
    public void setGroupLeader(User groupLeader){groupLeader = this.groupLeader;}
    public HashMap<User, Permissions> getUserPermissions(){return permissions;}
    public void setUserPermissions(HashMap<User, Permissions> permissions){ permissions = this.permissions;}
    
}