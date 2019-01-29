
function dataEdit(inStr, sAimID) { 
  var v1 = sAimID + ": " + inStr;
 
  var v2 = inStr.replace(/\x1D/g,"@");
  return v2;
}