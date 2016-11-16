package com.ohadr.c3p0_test;

public class ConnectionPoolStatus
{
	public int numBusyConnections;
	public int numBusyConnectionsAllUsers;

	public int numIdleConnections;
	public int numIdleConnectionsAllUsers;

	public int numConnections;
	public int numConnectionsAllUsers;
	
	public int numThreadsAwaitingCheckoutDefaultUser;
	
	public int numUnclosedOrphanedConnections;
	
	@Override
	public String toString()
	{
		return "numBusyConnections=" + numBusyConnections 
			+ " numBusyConnectionsAllUsers=" + numBusyConnectionsAllUsers
			+ " numIdleConnections=" + numIdleConnections
			+ " numIdleConnectionsAllUsers=" + numIdleConnectionsAllUsers
			+ " numConnections=" + numConnections
			+ " numConnectionsAllUsers=" + numConnectionsAllUsers
			
			+ " numThreadsAwaitingCheckoutDefaultUser=" + numThreadsAwaitingCheckoutDefaultUser
			
			+ " numUnclosedOrphanedConnections=" + numUnclosedOrphanedConnections;
	}
}
