import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;

import java.util.*;

public class Q1_TimeShared_1VM_10Cloudlets {

    public static void main(String[] args) {

        try {
            // STEP 1: Initialize CloudSim
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;

            CloudSim.init(num_user, calendar, trace_flag);

            // STEP 2: Create Datacenter
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // STEP 3: Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // STEP 4: Create 1 VM  ✅ (ONLY ONE VM)
            List<Vm> vmlist = new ArrayList<Vm>();

            int vmid = 0;
            int mips = 1000;
            long size = 10000;
            int ram = 512;
            long bw = 1000;
            int pesNumber = 1;
            String vmm = "Xen";

            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm,
                    new CloudletSchedulerTimeShared()); // ✅ TIME SHARED

            vmlist.add(vm);

            // STEP 5: Create 10 HOMOGENEOUS CLOUDLETS ✅
            List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();

            int id = 0;
            long length = 40000; // ✅ SAME for all (homogeneous)
            long fileSize = 300;
            long outputSize = 300;
            UtilizationModel utilizationModel = new UtilizationModelFull();

            for (int i = 0; i < 10; i++) {  // ✅ LOOP for 10 cloudlets
                Cloudlet cloudlet = new Cloudlet(i, length, pesNumber,
                        fileSize, outputSize,
                        utilizationModel, utilizationModel, utilizationModel);

                cloudlet.setUserId(brokerId);
                cloudlet.setVmId(vmid);

                cloudletList.add(cloudlet);
            }

            // STEP 6: Submit lists
            broker.submitVmList(vmlist);
            broker.submitCloudletList(cloudletList);

            // STEP 7: Start Simulation
            CloudSim.startSimulation();

            // STEP 8: Stop Simulation
            CloudSim.stopSimulation();

            // STEP 9: Output
            List<Cloudlet> newList = broker.getCloudletReceivedList();
            printCloudletList(newList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DATACENTER METHOD (NO CHANGE IN MOST QUESTIONS)
    private static Datacenter createDatacenter(String name) {

        List<Host> hostList = new ArrayList<Host>();

        int mips = 1000;
        List<Pe> peList = new ArrayList<Pe>();

        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        int hostId = 0;
        int ram = 2048;
        long storage = 1000000;
        int bw = 10000;

        hostList.add(
                new Host(
                        hostId,
                        new RamProvisionerSimple(ram),
                        new BwProvisionerSimple(bw),
                        storage,
                        peList,
                        new VmSchedulerTimeShared(peList)
                )
        );

        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, 10.0, 3.0, 0.05, 0.001, 0.0
        );

        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics,
                    new VmAllocationPolicySimple(hostList), new LinkedList<Storage>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    private static DatacenterBroker createBroker() throws Exception {
        return new DatacenterBroker("Broker");
    }

    private static void printCloudletList(List<Cloudlet> list) {
        for (Cloudlet cloudlet : list) {
            System.out.println("Cloudlet ID: " + cloudlet.getCloudletId() +
                    " Status: " + cloudlet.getStatus() +
                    " VM ID: " + cloudlet.getVmId() +
                    " Start Time: " + cloudlet.getExecStartTime() +
                    " Finish Time: " + cloudlet.getFinishTime());
        }
    }
}