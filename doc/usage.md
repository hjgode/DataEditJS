# Data Editing Plugin DataEditJS

This is a plugin for Honeywell Android devices. Data Editing Plugins can manipulate scanned barcode data before the data is transmitted to the system.

This plugin uses JavaScript to let you define the data changes. Using JavaScript enables you to use very flexible data manipulations.

A simple JavaScript is

    //replaces all FNC1 (\x1d) by @
    function dataEdit(inStr, sCodeID) { 
      var v1 = sCodeID + ": " + inStr;
      return v1;
    }

The first line is a comment and ignored by the interpreter.

The second line defines the function that is used by the plugin. The data editing function declaration has to be always the same.
The plugin calls the function and provides the barcode data and the barcode type as codeID as a string.
The function can then manipulate the data string and has to return the result as a string. This is then returned to the plugin and provided to the system.

The third line defines a variable named v1. This v1 is the result of concating the codeID, a colon and the barcode data.

The fourth line just returns the variable v1.

# Installation

Install the apk using ADB or Enterprise Provisioner.

## Activate the plugin manually

Go to "Settings" > "Scanning" > "Internal Scanner" > "Default Profile" > "Data Processing Settings" > "Data Editing Plugin"
Either select or enter the plugin package and class name: "hsm.dataeditjs/.DataEditJS"

## Activate the plugin automatically

Scan the following barcode to activate the plugin:

![barcode](https://github.com/hjgode/DataEditJS/raw/master/doc/activate_barcode.png)

## Activate by xml file

Use the following configuration xml to activate the plugin:

    <?xml version="1.0" standalone="yes"?>
    <ConfigDoc name="DataCollectionService">
        <Section name="wedge">
            <Section name="profiles">
                <Section name="dcs.scanner.imager">
                    <Section name="DEFAULT">
                        <Key name="DPR_EDIT_DATA_PLUGIN" path="/wedge/profiles/dcs.scanner.imager/DEFAULT/DATA_PROCESSING_SETTINGS/EDIT_DATA_PLUGIN">hsm.dataeditjs/.DataEditJS</Key>
                    </Section>
                </Section>
            </Section>
        </Section>
        <HHPReserved name="HHPReserved">
            <Section>
                <Key name="EXMVersion">1</Key>
            </Section>
        </HHPReserved>
    </ConfigDoc>

# Settings

## Options

The plugin actually supports two setting:

* JavaScript DataEditing enabled/disabled

This enables or disables the plugin without deactivating.

* Enable convert

If enabled, the plugin will replace all \r and \n by \\r and \\n, so the JavaScript code can work on this. Javascript does not support new lines within a variable.
This option is normally not needed as long as you do not use a Suffix or Prefix on \r or \n.

## The javascript code file

The JS code file has to be named dataedit.js and must be copied to the device's internal storage Documents directory. The file has to be in ISO-8859-1 encoding.

# Writing Javascript

As said, the plugin invokes a Javascript function named 'dataEdit' with two arguments, the first is the barcode data and the second the codeID. Now the Javascript can change the data as needed and must return the changed data as a string.

Reference: https://www.w3schools.com/js/default.asp and others

## Some basic Javascript usage

*Javascript is case sensitive
*Javascript does not need variables to be declared. But using 'var varname' is better.
*Variables can contain any type of variables: string, number, array etc.
*expression lines have to end with a semicolon
*strings can be surrounded by double or single quotes
*indices start with 0, so string.charAt(1) returns the second char.

### the if Condition
Using the if condition the following code is executed or not depending on the condition value.

    function dataEdit(inStr, sCodeID){
        var v="";
        if(sCodeID === 'b'){
            v=inStr.substr(2);
        }
        else{
            v=inStr;
        }
        return v;
    }

The above will cut the first character of the data if the codeID is 'b' (for CODE39).

## replace all characters by another

The following string manipulation replaces all occurences of the non-printable character 0x1d (29) by an @. This is using a regular expression.

    function dataEdit(inStr, sCodeID) { 
      var v2 = inStr.replace(/\x1D/g,"@");
      return v2;
    }

An alternative to the above:

    function dataEdit(inStr, sCodeID) {
        var str=inStr;
        var outStr="";
        for (var i = 0; i < str.length; i++) {
            if(str.charAt(i)=='\x1d'){
                outStr=outStr+'@';
            }
            else{
                outStr=outStr+str.charAt(i);
            }
        }
        return outStr;
    }

## Split data at FNC1

    //var str="1234\x1d567\x1d890";
    function dataEdit(inStr, sCodeID) {
        var str=inStr;
        var outStr="";
        for (var i = 0; i < str.length; i++) {
            if(str.charAt(i)=='\x1d'){
                outStr=outStr+'\t';
            }
            else{
                outStr=outStr+str.charAt(i);
            }
        }
        return outStr;
    }
    // gives 1234\t567\t890

Using string.split()

    //var str="1234\x1d567\x1d890";
    function dataEdit(inStr, sCodeID) {
        var str=inStr;
        var outStr="";
        var parts=inStr.split('\x1d');
        for(i=0; i<parts.length; i++){
            outStr=outStr+parts[i]+"\t";
        }
        //or use join
        //outStr=parts.join('\t');
        
        return outStr;
    }

## Find a pattern

    //var str="1234567890";
    function dataEdit(inStr, sCodeID) {
        var str=inStr;
        var outStr="";
        var pos = inStr.indexOf('456');
        if(pos == -1){
            outStr="not found";
        }else{
            //cut this pattern
            outStr=inStr.substr(0,pos) + inStr.substr(pos+3);
        }
        return outStr;
    }
    //1237890

## using custom functions

    function stripFromEnd(inp,num){
        out=inp.substr(0,inp.length-num);
        return out;
    }
    
    function dataEdit(inStr,inCodeID){
        var outStr="";
        outStr=stripFromEnd(inStr,2);
        var pos = outStr.indexOf('456');
        inStr=outStr;
        if(pos == -1){
            outStr="not found";
        }else{
            //cut this pattern
            outStr=inStr.substr(0,pos) + inStr.substr(pos+3);
        }
        return outStr;
    }

# Testing your code

In the internet are various online javascript interpreters. For example https://www.jdoodle.com/execute-rhino-online

![jdoodle](https://github.com/hjgode/DataEditJS/raw/master/doc/jdoodle.png)

or (better): https://www.onlinegdb.com/online_javascript_rhino_interpreter

![onlinedgb](https://github.com/hjgode/DataEditJS/raw/master/doc/js-onlinegdb_com.png)

##Testing in onlinedgb

Open the web site at https://www.onlinegdb.com/online_javascript_rhino_interpreter and enter this script:

    function stripFromEnd(inp,num){
        out=inp.substr(0,inp.length-num);
        return out;
    }
    
    function dataEdit(inStr,inCodeID){
        var outStr="";
        outStr=stripFromEnd(inStr,2);
        var pos = outStr.indexOf('456');
        inStr=outStr;
        if(pos == -1){
            outStr="not found";
        }else{
            //cut this pattern
            outStr=inStr.substr(0,pos) + inStr.substr(pos+3);
        }
        return outStr;
    }
    print(dataEdit("1234567890","b"));
    
Then click on the green run symbol at top of the script window to run the script in Interactive Console mode. You will get a new black Input window at the bottom showing the ouput of the print command.

For the above example you will get "12378" as we called dataEdit with barcode data=1234567890 and code ID=b.

![onlinedgb](https://github.com/hjgode/DataEditJS/raw/master/doc/js-onlinegdb_com_example.png)

You can test now different code and input and verify you get the needed result.
