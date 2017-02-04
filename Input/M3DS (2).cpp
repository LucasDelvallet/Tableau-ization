ESign FaceBSP::sign(const Vector3 &p) const {
    ESign res=SIGN_NONE;

    Vector3 newP = Vector3(_tabVertex[0]->point(), p);
    Vector3 normalFace = this->_normal;
    double scalaire = normalFace.dot(newP);
    if(_tabVertex.size()>0){
        if(scalaire < 0.)
            res = SIGN_MINUS;
        else
            res = SIGN_PLUS;
    }

    return res;
}



Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {
    Vector3 res;


    Vector3 p1p2(p1,p2);

   
    double p1p1ScalN = p1p2.dot(_normal);
    if (fabs(p1p1ScalN) < 0.01 )
        return Vector3((p1.x()+p2.x())/2,(p1.y()+p2.y())/2,(p1.z()+p2.z())/2);

    double k = (point(0).dot(_normal) - p1.dot(_normal)) / p1p1ScalN;

    if ( fabs(k) < 0.01)
        return p1;
    res = p1 + k * p1p2;


    return res;
}


void FaceBSP::separe(const FaceBSP &f) {
    
    bool estPositif;
    VertexBSP *tmp = _tabVertex[0], *anc;
    ESign sign = f.sign(tmp->point());
    if(sign == SIGN_MINUS){
        vertexNegative.push_back(tmp);
    } else if(sign == SIGN_PLUS){
        vertexPositive.push_back(tmp);
    }

    for(int i = 1; i<_tabVertex.size(); i++){
        anc = tmp;
        tmp = _tabVertex[i];
        sign = f.sign(tmp->point());
        if(sign != SIGN_NONE){
            if(f.sign(anc->point()) != sign){
                VertexBSP *inter=createVertex(f.intersection(_tabVertex[i-1]->point(),tmp->point()));
                inter->interpolateNormal(*_tabVertex[i-1],*tmp);
                vertexNegative.push_back(inter);
                vertexPositive.push_back(inter);
            }
        }
        if(sign == SIGN_MINUS){
            vertexNegative.push_back(tmp);
        } else if(sign == SIGN_PLUS){
            vertexPositive.push_back(tmp);
        }
    }
    anc = tmp;
    tmp = _tabVertex[0];

    sign = f.sign(tmp->point());
    if(f.sign(anc->point()) != sign){
        VertexBSP *inter=createVertex(f.intersection(_tabVertex[_tabVertex.size()-1]->point(),tmp->point()));
        inter->interpolateNormal(*_tabVertex[_tabVertex.size()-1],*tmp);
        vertexNegative.push_back(inter);
        vertexPositive.push_back(inter);
    }
}



