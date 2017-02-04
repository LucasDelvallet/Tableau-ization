
ESign FaceBSP::sign(const Vector3 &p) const {

  ESign res=SIGN_NONE;

  Vector3 vec(_tabVertex[0]->point(),p);

  if (vec.dot(_normal) >= 0) {
      res = SIGN_PLUS;
  } else {
      res = SIGN_MINUS;
  }

  return res;
}

Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {

  Vector3 p1p2 = p2 - p1;

  if(p1p2.dot(normal()) < 0.2 && p1p2.dot(normal()) > -0.2){
      return Vector3((p1.x()+p2.x())/2,(p1.y()+p2.y())/2,(p1.z()+p2.z())/2);
  }

  double k = (point(0).dot(normal()) - p1.dot(normal())) / (p1p2.dot(normal())) ;
  Vector3 I = p1 + (k) * (p1p2);


  return I;
}


void FaceBSP::separe(const FaceBSP &f) {

  int previous_point_index = _tabVertex.size() - 1 ;
  Vector3 p_previous = this->point(previous_point_index);
  ESign p_previous_sign = f.sign(p_previous);

  for(int j = 0; j < _tabVertex.size(); j++){
      Vector3 pj = this->point(j);
      ESign pj_sign = f.sign(pj);

      if(pj_sign != p_previous_sign){
          Vector3 intersection  = f.intersection(p_previous, pj);
          VertexBSP *inter = createVertex(intersection);
          inter->interpolateNormal(*_tabVertex[previous_point_index],*_tabVertex[j]);
          vertexNegative.push_back(inter);
          vertexPositive.push_back(inter);
      }
      if (pj_sign == SIGN_MINUS) {
          vertexNegative.push_back(_tabVertex[j]);
      } else {
          vertexPositive.push_back(_tabVertex[j]);
      }

      previous_point_index = j;
      p_previous = pj;
      p_previous_sign = pj_sign;
  }

}


