
ESign FaceBSP::sign(const Vector3 &p) const {
ESign res=SIGN_NONE;
if(((_tabVertex[0]->point()-p).dot(_normal))<=0)
   res=SIGN_MINUS;
   else  res=SIGN_PLUS;
  return res;
}


Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {
  Vector3 res;
  float num,cart,rapport;


  num=normal().dot(point(1)-p1);
  cart=normal().dot(p2-p1);
   rapport=num/cart;
  res=rapport*(p2-p1)+p1;
  return res;
}


void FaceBSP::separe(const FaceBSP &f) {

}