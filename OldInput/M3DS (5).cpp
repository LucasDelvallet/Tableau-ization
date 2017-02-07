
ESign FaceBSP::sign(const Vector3 &p) const {

  ESign res=SIGN_NONE;

  Vector3 ap(p,_tabVertex[0]->point());

  if(_normal.dot(ap)>=0){
      res=SIGN_PLUS;
  }
  else {
      res=SIGN_MINUS;
  }

  return res;
}


Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {
  Vector3 res;

  
  Vector3 p1p2(p1,p2);
  double div = p1p2.dot(_normal);
  if (fabs(div) < 0.01 ) return Vector3((p1.x()+p2.x())/2,(p1.y()+p2.y())/2,(p1.z()+p2.z())/2);
  double k = (point(0).dot(_normal)-p1.dot(_normal))/div;
  res = p1 + (k*p1p2);

  return res;
}


void FaceBSP::separe(const FaceBSP &f) {

  int size = _tabVertex.size();
  int last_index = size -1;
  Vector3 last_point = this->point(last_index);
  ESign last_sign = f.sign(last_point);

  for(int i=0;i<size;i++){
    Vector3 current_point = this->point(i);
    ESign current_sign = f.sign(current_point);

    if(current_sign != last_sign){ 
        Vector3 intersection = f.intersection(current_point, last_point);
        VertexBSP *intersect = createVertex(intersection); 
        intersect->interpolateNormal(*_tabVertex[i],*_tabVertex[last_index]); 
        vertexNegative.push_back(intersect);
        vertexPositive.push_back(intersect);
    }

    last_point = current_point;
    last_sign = current_sign;
    last_index = i;

    if (current_sign == SIGN_MINUS) {
        vertexNegative.push_back(_tabVertex[i]);
    } else {
        vertexPositive.push_back(_tabVertex[i]);
    }

  }

}


