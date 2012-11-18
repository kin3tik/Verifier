Online Handwritten Signature Verification System
================================================

##About

This Java-based online handwritten signature verification (OHSV) system implements a parametric 
technique of signature verification. This system aimed to keep error rates such as FAR 
(False Acceptance Rate), FRR (False Rejection Rate) and EER (Equal Error Rate) as low or as close 
to 0% as possible, but has achieved ERR of approximately 11%, or FAR = ~8% FRR = ~14% in a best 
case scenario (using a test database).

The system takes multiple genuine signatures (1...*, but at least 5-10 preferably), calculates a
threshold value (using weighted Euclidean distance) based on the chosen global features and builds
a "reference signature". The system then allows test signature comparisons with the reference 
signature, judging the legitimacy of the test signature.

##Usage

There are two options when running this software – a CLI with a limited feature set, and a GUI.

###CLI Usage

From the terminal/command prompt, navigate to the directory where the *.JAR file is stored and 
type the following:
`java –jar hsv.jar refSig.file testSig.file`

####Parameters

* refSig.file – the signature file that contains the multiple signatures used to create the 
reference signature. Usually this file should contain 5-10 signature samples.
* testSig.file - the signature file that contains a single signature that we want to verify 
the authenticity of.

###GUI Usage

To launch the GUI, simply double click the runnable jar file, or run this CLI command without an 
argument, like so:
`java –jar hsv.jar`

##System Requirements and Dependancies

* Java JRE 1.6+(to run)
* Should work on any OS with adequte JRE
* Libraries
	* JUnit 4.0
	* JFreeChart 1.0.14
	
##Other

Refer to `docs\Project Report.pdf` for a much more detailed overview.