
ESign FaceBSP::sign(const Vector3 &p) const {
    Vector3 ap = p - _tabVertex[0]->point();

    double res =_normal.dot(ap);

    if(res<0.0){
        return SIGN_MINUS;
    } else {
        return SIGN_PLUS;
    }
}


Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {

    Vector3 res;

    Vector3 A = _tabVertex[0]->point();
    Vector3 Ap1 = p1 - A;
    Vector3 p1p2 = p2 - p1;

    double sAp1 = _normal.dot(Ap1);
    double sp1p2 = _normal.dot(p1p2);

    double k = fabs(sAp1)/fabs(sp1p2);

    res = p1+k*p1p2;

    return res;
}



void FaceBSP::separe(const FaceBSP &f) {
  

    int size = _tabVertex.size();
    int last_index = size -1;
    Vector3 last_point = this->point(last_index);
    ESign last_sign = f.sign(last_point);

    for(int i=0; i<size; i++){

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
