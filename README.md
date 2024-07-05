The "Rhodium" project is designed to investigate and identify blind spots in the coverage of mobile phone networks in indoor environments. The aim of this project is to provide an efficient tool for users to identify weak points or lack of mobile network coverage. In the following, the steps of this project are explained in detail:

1. Entering the environment map:
 - The user first enters the desired environment map into the program.
 - The map is displayed in the program so that the user can see his route.

2. Tracking the user's movement path:
 - By moving the phone in the environment, the user sees the direction of his movement on the map.
 - In case of deviation, the user can click on the map to select the correct point again.

3. Continuous measurement of received power parameters:
 - The UE device (User Equipment) continuously measures the parameters related to the received power. These parameters may include information about the serving cell and neighboring cells.
 - The measured parameters may include the following:
 - For GSM: C1, C2, RXLev
 - For UMTS: RSCP, EC/NO
 - For LTE: RSRP, CINR, RSRQ

4. Record the measured data:
 - Every four seconds, the Android application receives the measured values.
 - These values ​​are recorded in the database along with the UE location and parameters related to the serving cell (such as PLMN, LAC, RAC, TAC, cell ID).

5. Display the recorded data on the map:
 - The user can view the stored data online or offline.
 - A map similar to OSM (OpenStreetMap) maps is displayed to the user.
 - New locations are recorded as colored dots on the map. These colors are determined based on the measurement of the received power of the cell and may be as follows:
 - Green: strong coverage
 - Yellow: medium coverage
 - Orange: poor coverage
 - Red: Very poor coverage
 - Black: Failure to measure

By using these steps, users can easily identify the blind and weak spots of mobile phone network coverage in indoor environments and take action to improve the coverage.[Rhodium_Project_Document.pdf](https://github.com/user-attachments/files/16113857/Rhodium_Project_Document.pdf)
