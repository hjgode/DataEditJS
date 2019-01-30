//replaces all FNC1 (\x1d) by @ and adds CR LF to the end
function addCRLF(str){
  var s = str + "\n\r";
  return s;
}

function dataEdit(inStr, sAimID) { 
  //replaces all FNC1 (\x1d) by @
  var v2 = inStr.replace(/\x1D/g,"@");
  v2 = addCRLF(v2);
  return v2;
}