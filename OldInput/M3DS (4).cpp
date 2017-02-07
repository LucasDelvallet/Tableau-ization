
ESign FaceBSP::sign(const Vector3 &p) const {

  ESign res=SIGN_NONE;

  Vector3 point = _tabVertex[0]->point();
  if(Vector3(point,p).dot(_normal) >= 0)
      res = SIGN_PLUS;
  else res = SIGN_MINUS;

  return res;
}


Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {

  Vector3 res;

  Vector3 seg(p1,p2);
 
  if (seg.dot(_normal) < 0.001 && seg.dot(_normal) > -0.001) return p1;
  double k = ((point(0)-p1).dot(_normal)) / (seg.dot(_normal));
  res = p1 + k * seg;

  return res;
}



void FaceBSP::separe(const FaceBSP &f) {

  Vector3 last = this->point(_tabVertex.size()-1);
  int lastIndex = _tabVertex.size()-1;

  for(int i = 0; i<_tabVertex.size(); i++)
  {
    if(f.sign(this->point(i)) != f.sign(last)){
      VertexBSP *inter = createVertex(f.intersection(this->point(i),last));
      inter->interpolateNormal(*_tabVertex[i], *_tabVertex[lastIndex]);
      vertexNegative.push_back(inter);
      vertexPositive.push_back(inter);
    }

    last = this->point(i);
    lastIndex = i;

    if(f.sign(this->point(i)) == SIGN_PLUS)
        vertexPositive.push_back(_tabVertex[i]);
    else vertexNegative.push_back(_tabVertex[i]);
  }

}
