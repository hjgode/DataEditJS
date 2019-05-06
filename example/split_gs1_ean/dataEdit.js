function myStartsWith(inStr, lookfor) {
   if(inStr.length < lookfor.length) {
	return false;
  }
  front=inStr.substring(0,lookfor.length);
  if(front == lookfor) {
    return true;
  }
  return false;
}

   var testStr = [ "020840159020016215200103370088", "10S010330201", "00984296833021002023" ];

function dataEdit(inStr, sAimID) { 
 
 var vOut=inStr;
  if( myStartsWith(inStr, "02") ){
    vOut = inStr.slice(2, 16) + " " + inStr.slice(18, 24) +  " " + inStr.slice (26);
    return vOut;
  }
  if(myStartsWith(inStr, "10")) {
    vOut=inStr.slice(2);
    return vOut;
  }
  if(myStartsWith(inStr, "00")) {
    vOut=inStr.slice(2);
    return vOut;
  }

  return vOut;
}
