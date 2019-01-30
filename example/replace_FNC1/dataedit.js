//replaces all FNC1 (\x1d) by @
function dataEdit(inStr, sAimID) { 
  var v2 = inStr.replace(/\x1D/g,"@");
  return v2;
}