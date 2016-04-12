package com.inspur.ics.client.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;

import com.inspur.ics.client.DataStoreService;
import com.inspur.ics.client.rest.DataStoreRestService;
import com.inspur.ics.client.support.ExecutorFactory;
import com.inspur.ics.client.support.RemoteException;
import com.inspur.ics.common.Types.TaskTargetType;
import com.inspur.ics.common.util.FormatUtil;
import com.inspur.ics.core.task.TaskIntermediateResult;
import com.inspur.ics.framework.result.Result;
import com.inspur.ics.pojo.DataStore;
import com.inspur.ics.pojo.DataStoreDir;
import com.inspur.ics.pojo.DataStoreFile;
import com.inspur.ics.pojo.Host;
import com.inspur.ics.pojo.NfsDataStore;
import com.inspur.ics.pojo.OcfsDataStore;
import com.inspur.ics.pojo.StandardPortGroup;
import com.inspur.ics.pojo.StandardSwitch;
import com.inspur.ics.pojo.VSanDataStore;
import com.inspur.ics.pojo.VSanMon;
import com.inspur.ics.pojo.VSanOSD;

/**
 * Implementation of DataStore interface.
 * @author jiangwt
 */

public class DataStoreServiceImpl implements DataStoreService {

	    /**
	     * Datastore rest service.
	     */
	    private DataStoreRestService DataStoreRestService;

	    /**
	     * user token.
	     */
	    private String token;

	    /**
	     * @param url the path of storage rest service.
	     * @param token user token
	     */
	    public  DataStoreServiceImpl(String url, String token) {
	        DataStoreRestService = ProxyFactory.create(DataStoreRestService.class, url, ExecutorFactory.getDefaultClientExecutor());
	        this.token = token;
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult attachDataStoreToHost(List<String> addDsUuids, String hostUuid) {

	    	if (addDsUuids == null || addDsUuids.isEmpty()) {
	            throw new RemoteException("The list of addDsUuids cannot be null or empty.");
	        }

	        for (String dsuuid : addDsUuids) {
	        	if (!dsuuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	        		throw new RemoteException("The dsuuid of addDsUuids is invalid.");
	            }
	        }
	        
	        if (hostUuid == null || hostUuid.isEmpty()) {
	        	throw new RemoteException("The uuid of host cannot be null or empty.");
	        }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	        	throw new RemoteException("The uuid of host is invalid.");
	        }
	        
	        String resultXML = DataStoreRestService.attachDataStoreToHost(token, addDsUuids, hostUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult attachHostToDataStore(List<String> addHostUuids, String dsUuid) {
	    	
	    	if (addHostUuids == null || addHostUuids.isEmpty()) {
	            throw new RemoteException("The list of addHostUuids cannot be null or empty.");
	        }

	        for (String hostuuid : addHostUuids) {
	            if (!hostuuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	                throw new RemoteException("The hostuuid of addHostUuids is invalid.");
	            }
	        }
	        
	        if (dsUuid == null || dsUuid.isEmpty()) {
	        	throw new RemoteException("The uuid of datastore cannot be null or empty.");
	        }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	        }

	        String resultXML = DataStoreRestService.attachHostToDataStore(token, addHostUuids, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult createExternalVsanDataStore(VSanDataStore vsanDataStore) {
	    	
	    	String resultXML = DataStoreRestService.createExternalVsanDataStore(token, FormatUtil.toJson(vsanDataStore));
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

		    if (result.getError() != null) {
		        throw new RemoteException(result.getError().getMessage());
		    } else {
		        return (TaskIntermediateResult) ((List) result.getData()).get(0);
		    } 	
	    	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult createNfsDataStore(NfsDataStore nfsDataStore) {
	    	
	    	 String resultXML = DataStoreRestService.createNfsDataStore(token, FormatUtil.toJson(nfsDataStore));
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
             System.out.println(resultXML);
		     if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult createOcfsDataStore(OcfsDataStore ocfsDataStore, String hostUuid){
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	    		throw new RemoteException("The uuid of host cannot be null or empty.");
	    	}

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	        	throw new RemoteException("The uuid of host is invalid.");
	        }
	        
	        String resultXML = DataStoreRestService.createOcfsDataStore(token, FormatUtil.toJson(ocfsDataStore), hostUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		    	throw new RemoteException(result.getError().getMessage());
		    } else {
		    	return (TaskIntermediateResult) ((List) result.getData()).get(0);
		    } 	
	    }

	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult createVsanDataStore(VSanDataStore vsanDataStore) {
	    	
	    	String resultXML = DataStoreRestService.createVsanDataStore(token, FormatUtil.toJson(vsanDataStore));
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

		    if (result.getError() != null) {
		        throw new RemoteException(result.getError().getMessage());
		    } else {
		        return (TaskIntermediateResult) ((List) result.getData()).get(0);
		    } 	
	    	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult detachDataStoreFromHost(List<String> removeDsUuids, String hostUuid) {
	    	
	    	if (removeDsUuids == null || removeDsUuids.isEmpty()) {
	            throw new RemoteException("The list of removeDsUuids cannot be null or empty.");
	        }

	        for (String dsuuid : removeDsUuids) {
	            if (!dsuuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	                throw new RemoteException("The dsuuid of removeDsUuids is invalid.");
	            }
	        }
	        
	        if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	        
	        String resultXML = DataStoreRestService.detachDataStoreFromHost(token, removeDsUuids, hostUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult detachHostFromDataStore(List<String> removeHostUuids, String dsUuid){
	    	
	    	if (removeHostUuids == null || removeHostUuids.isEmpty()) {
	            throw new RemoteException("The list of removeHostUuids cannot be null or empty.");
	        }

	        for (String hostuuid : removeHostUuids) {
	            if (!hostuuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	                throw new RemoteException("The hostuuid of removeHostUuids is invalid.");
	            }
	        }
	        
	        if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	        
	        String resultXML = DataStoreRestService.detachHostFromDataStore(token, removeHostUuids, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult extendOcfsDataStore(String dsUuid, String hostUuid) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	        
	        if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	        
	        String resultXML = DataStoreRestService.extendOcfs(token, dsUuid, hostUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult extendVsanDataStore(String vsanUuid, VSanOSD osd) {
	    	
	    	if (vsanUuid == null || vsanUuid.isEmpty()) {
	             throw new RemoteException("The uuid of vsan cannot be null or empty.");
	         }
	    	
	    	String resultXML = DataStoreRestService.extendVsan(token, vsanUuid, FormatUtil.toJson(osd));
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 		    
	    	
	    }
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<DataStore> getAllDataStore() {
	    	
	    	String resultXML = DataStoreRestService.getAll(token);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class, NfsDataStore.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return new ArrayList<DataStore>();
	        }  else {
	            return (List<DataStore>) result.getData();
	        }
	    	
	    }
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<DataStore> getAvailableDataStoreOnHost(String hostUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	         if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	    	 
	         String resultXML = DataStoreRestService.getAvailableDataStoreOnHost(token, hostUuid);
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class});
	         
		     if (result.getError() != null) {
		            throw new RemoteException(result.getError().getMessage());
		        }  else if (result.getData() == null) {
		            return new ArrayList<DataStore>();
		        }  else {
		            return (List<DataStore>) result.getData();
		        }
	    }
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<Host> getAvailableHostOnDataStore(String dsUuid) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	         if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	    	
	         String resultXML = DataStoreRestService.getAvailableHostOnDataStore(token, dsUuid);
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, Host.class});
	         
		     if (result.getError() != null) {
		            throw new RemoteException(result.getError().getMessage());
		        }  else if (result.getData() == null) {
		            return new ArrayList<Host>();
		        }  else {
		            return (List<Host>) result.getData();
		        }
	    }
	    
	    @SuppressWarnings({ "unchecked" })
	    @Override
	    public List<StandardPortGroup> getAvailablePortGroupForVsan(String hostUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	    	
	    	String resultXML = DataStoreRestService.getAvailablePortGroupForVsan(token, hostUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class, StandardSwitch.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return new ArrayList<StandardPortGroup>();
	        }  else {
	            return (List<StandardPortGroup>) result.getData();
	        }
	    }
	    
	    @SuppressWarnings({ "unchecked" })
	    @Override
	    public DataStoreDir getDataStoreDirectoryTree(String dsUuid) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	    	
	        String resultXML = DataStoreRestService.getDataStoreDirectoryTree(token, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStoreDir.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return null;
	        }  else {
	            return ((List<DataStoreDir>) result.getData()).get(0);
	        }
	    }
	    
	    @SuppressWarnings({ "unchecked" })
	    @Override
	    public List<DataStoreFile> getDataStoreFile(String dsUuid, String dir) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	        
	        String resultXML = DataStoreRestService.getDataStoreFile(token, dsUuid, dir);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStoreFile.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return new ArrayList<DataStoreFile>();
	        }  else {
	            return (List<DataStoreFile>) result.getData();
	        }
	    	
	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public DataStore getDataStoreInfo(String dsUuid) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	            throw new RemoteException("The uuid of datastore cannot be null or empty.");
	        }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	            throw new RemoteException("The uuid of datastore is invalid.");
	        }

	        String resultXML = DataStoreRestService.getInfo(token, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class, NfsDataStore.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return null;
	        }  else {
	            return ((List<DataStore>) result.getData()).get(0);
	        }
	    }
	    
	    
	    @SuppressWarnings({ "unchecked" })
	    @Override
	    public List<DataStore> getDataStoreList(TaskTargetType targetType, String targetUuid) {
	    	
	    	if (targetType == null) {
	             throw new RemoteException("The targetType cannot be null.");
	         }
	        
	        if (targetUuid == null || targetUuid.isEmpty()) {
	             throw new RemoteException("The targetUuid cannot be null or empty.");
	         }
	        
	        String resultXML = DataStoreRestService.getDataStoreList(token, targetType, targetUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class, NfsDataStore.class});
	         
		    if (result.getError() != null) {
		           throw new RemoteException(result.getError().getMessage());
		       }  else if (result.getData() == null) {
		           return new ArrayList<DataStore>();
		       }  else {
		           return (List<DataStore>) result.getData();
		       }
	        	    	
	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<DataStore> getDataStoreOnHost(String hostUuid) {
	    	 
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	         if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	         
	         String resultXML = DataStoreRestService.getDataStoreOnHost(token, hostUuid);
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class,NfsDataStore.class});
	         
		     if (result.getError() != null) {
		            throw new RemoteException(result.getError().getMessage());
		        }  else if (result.getData() == null) {
		            return new ArrayList<DataStore>();
		        }  else {
		            return (List<DataStore>) result.getData();
		        }
	    }
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public DataStore getDeviceBackingAndMountStatus(String dsUuid) {
	    	
	    	 if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	         if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	    	
	         String resultXML = DataStoreRestService.getDeviceBackingAndMountStatus(token, dsUuid);
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, DataStore.class,NfsDataStore.class});
		     
		     if (result.getError() != null) {
		            throw new RemoteException(result.getError().getMessage());
		        }  else if (result.getData() == null) {
		            return null;
		        }  else {
		            return ((List<DataStore>) result.getData()).get(0);
		        }
	    }
	    
//	    @SuppressWarnings({ "rawtypes" })
//	    @Override
//	    public TaskIntermediateResult modifyOcfsDataStoreOwner(OcfsDataStore ods, String opHostUuid) {
//	    	
//	    	if (opHostUuid == null || opHostUuid.isEmpty()) {
//	             throw new RemoteException("The opHostUuid cannot be null or empty.");
//	         }
//	    	
//	    	if (!opHostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
//	             throw new RemoteException("The uuid of opHost is invalid.");
//	         }
//	    	
//	    	String resultXML = DataStoreRestService.modifyOcfsOwner(token, FormatUtil.toJson(ods), opHostUuid);
//		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
//	    	
//		    if (result.getError() != null) {
//		         throw new RemoteException(result.getError().getMessage());
//		     } else {
//		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
//		     } 	
//	    }
//	    
//	    
//	    @SuppressWarnings({ "rawtypes" })
//	    @Override
//	    public TaskIntermediateResult modifyNfsDataStoreOwner(NfsDataStore nds) {
//	    	
//	    	String resultXML = DataStoreRestService.modifyNfsOwner(token, FormatUtil.toJson(nds));
//		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
//	    	
//		    if (result.getError() != null) {
//		         throw new RemoteException(result.getError().getMessage());
//		     } else {
//		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
//		     } 	
//	    }
	    
	    
	    @SuppressWarnings("unchecked")
	    @Override
	    public List<Host> getHostOnDataStore(String dsUuid) {
	    	
	    	 if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	         if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	    	
	         String resultXML = DataStoreRestService.getHostOnDataStore(token, dsUuid);
		     Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, Host.class});
	         
		     if (result.getError() != null) {
		            throw new RemoteException(result.getError().getMessage());
		        }  else if (result.getData() == null) {
		            return new ArrayList<Host>();
		        }  else {
		            return (List<Host>) result.getData();
		        }
	    }
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    @Override
	    public StandardPortGroup getHostVsanPortGroup(String hostUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	    	
	    	String resultXML = DataStoreRestService.getVsanPortGroup(token, hostUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, StandardPortGroup.class, StandardSwitch.class});
	        
	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return null;
	        }  else {
	        	System.out.println(((List)result.getData()).size());
	            return ((List<StandardPortGroup>) result.getData()).get(0);
	        }
	    }

		@SuppressWarnings({ "unchecked" })
	    @Override
	    public List<VSanMon> getVsanMonitorList(String vsanUuid) {
	    	
	    	if (vsanUuid == null || vsanUuid.isEmpty()) {
	             throw new RemoteException("The uuid of vsan cannot be null or empty.");
	         }
	    	
	    	String resultXML = DataStoreRestService.getVsanMonitorList(token, vsanUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, VSanMon.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return new ArrayList<VSanMon>();
	        }  else {
	            return (List<VSanMon>) result.getData();
	        }
	    }
	    
	    @SuppressWarnings({ "unchecked" })
	    @Override
	    public List<VSanOSD> getVsanOsdList(String vsanUuid) {
	    	
	    	if (vsanUuid == null || vsanUuid.isEmpty()) {
	             throw new RemoteException("The uuid of vsan cannot be null or empty.");
	         }
	    	
	    	String resultXML = DataStoreRestService.getVsanOsdList(token, vsanUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, VSanOSD.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        }  else if (result.getData() == null) {
	            return new ArrayList<VSanOSD>();
	        }  else {
	            return (List<VSanOSD>) result.getData();
	        }
	    }
	    
//	    @SuppressWarnings({ "unchecked" })
//	    @Override
//	    public List<BlockDevice> autoSelectVsanOsds(List<VSanOSD> osdList) {
//	    	List osdList1 = new ArrayList<String>();
//	    	for(VSanOSD vsanOSD : osdList ){
//	    		String str = FormatUtil.toJson(vsanOSD);
//	    		osdList1.add(str);
//	    	}
//	    	String resultXML = DataStoreRestService.autoSelectVsanOsd(token, osdList1);
//		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, BlockDevice.class});
//	         
//		    if (result.getError() != null) {
//		           throw new RemoteException(result.getError().getMessage());
//		       }  else if (result.getData() == null) {
//		           return new ArrayList<BlockDevice>();
//		       }  else {
//		           return (List<BlockDevice>) result.getData();	    	
//	          }
//	    }
//	    
//	    @Override
//	    public int getDefautReplicas() {
//	    	
//	    	String resultXML = DataStoreRestService.getDefautReplicas(token);
//        	System.out.println(resultXML);
//        	Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class});
//        	if (result.getError() != null) {
//		           throw new RemoteException(result.getError().getMessage());
//		       }  else if (result.getData() == null) {
//		           return 0;
//		       }  else {
//		           return (Integer) ((List) result.getData()).get(0);	    	
//	          }
//	    	
//	    }
	    
	    @Override
		public void immigrateDataStoreFromV3ToV4() {

	    	String resultXML = DataStoreRestService.immigrateDataStoreFromV3ToV4(token);
	    	Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class});

	    	if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
	    	}
		}

	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult modifyDataStoreName(String dsUuid, String dsName) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	        
	        if (dsName == null || dsName.isEmpty()) {
	             throw new RemoteException("The name of datastore cannot be null or empty.");
	         }
	        
	        String resultXML = DataStoreRestService.modify(token, dsUuid, dsName);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult modifyOcfsDataStoreNodeSlot(String odsUuid, int newNodeSlot, String opHostUuid){
	    	
	    	if (odsUuid == null || odsUuid.isEmpty()) {
	             throw new RemoteException("The odsUuid cannot be null or empty.");
	         }
	    	
	    	if (opHostUuid == null || opHostUuid.isEmpty()) {
	             throw new RemoteException("The opHostUuid cannot be null or empty.");
	         }
	    	
	    	if (!opHostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of opHost is invalid.");
	         }
	    	
	    	String resultXML = DataStoreRestService.modifyOcfsNodeSlot(token, odsUuid, newNodeSlot, opHostUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult mountDataStore(String hostUuid, String dsUuid) {
	    	 // check parameter(s).
	    	 if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	         if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	         
	         if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	         if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	         
	        String resultXML = DataStoreRestService.mount(token, hostUuid, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult removeDataStore(String dsUuid) {
	    	
	    	if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	        if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	    	
	        String resultXML = DataStoreRestService.remove(token, dsUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    }
	    
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult removeHostVsanPortGroup(String hostUuid, String pgUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	        
	        if (pgUuid == null || pgUuid.isEmpty()) {
	             throw new RemoteException("The uuid of port group cannot be null or empty.");
	         }
	        String resultXML = DataStoreRestService.removeVsanPortGroup(token, hostUuid, pgUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 		
	        
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult setHostVsanPortGroup(String hostUuid, String pgUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	        
	        if (pgUuid == null || pgUuid.isEmpty()) {
	             throw new RemoteException("The uuid of port group cannot be null or empty.");
	         }
	        String resultXML = DataStoreRestService.setVsanPortGroup(token, hostUuid, pgUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 		
	        
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult shrinkVsanDataStore(String vsanUuid, String osdUuid) {
	    	
	    	if (vsanUuid == null || vsanUuid.isEmpty()) {
	             throw new RemoteException("The uuid of vsan cannot be null or empty.");
	         }
	    	if (osdUuid == null || osdUuid.isEmpty()) {
	             throw new RemoteException("The uuid of osd cannot be null or empty.");     
	         }
	    	
	    	String resultXML = DataStoreRestService.shrinkVsan(token, vsanUuid, osdUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 		
	    	
	    }
	    
	    
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult synchronizeAllDataStore(String hostUuid) {
	    	
	    	if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	        if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	        
	        String resultXML = DataStoreRestService.syncAllDataStore(token, hostUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 	
	    }
	    
	    @SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult syncVsanConfigInCluster(String vsanUuid) {
	    	
	    	if (vsanUuid == null || vsanUuid.isEmpty()) {
	             throw new RemoteException("The uuid of vsan cannot be null or empty.");
	         }
	    	
	    	String resultXML = DataStoreRestService.syncVsanConfigInCluster(token, vsanUuid);
		    Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});
	    	
		    if (result.getError() != null) {
		         throw new RemoteException(result.getError().getMessage());
		     } else {
		         return (TaskIntermediateResult) ((List) result.getData()).get(0);
		     } 		
	    }

		@SuppressWarnings({ "rawtypes" })
	    @Override
	    public TaskIntermediateResult unmountDataStore(String hostUuid, String dsUuid) {
	    	 // check parameter(s).
	    	 if (hostUuid == null || hostUuid.isEmpty()) {
	             throw new RemoteException("The uuid of host cannot be null or empty.");
	         }

	         if (!hostUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of host is invalid.");
	         }
	         
	         if (dsUuid == null || dsUuid.isEmpty()) {
	             throw new RemoteException("The uuid of datastore cannot be null or empty.");
	         }

	         if (!dsUuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")) {
	             throw new RemoteException("The uuid of datastore is invalid.");
	         }
	         
	        String resultXML = DataStoreRestService.unmount(token, hostUuid, dsUuid);
	        Result result = (Result) FormatUtil.fromXML(resultXML, new Class[]{Result.class, TaskIntermediateResult.class});

	        if (result.getError() != null) {
	            throw new RemoteException(result.getError().getMessage());
	        } else {
	            return (TaskIntermediateResult) ((List) result.getData()).get(0);
	        } 	
	    	
	    }
 }
	    
	    
