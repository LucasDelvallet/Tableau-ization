Vector3 FaceBSP::intersection(const Vector3 &p1,const Vector3 &p2) const {
    Vector3 res;

    float numerateur=normal().dot(point(1)-p1);
    float denominateur=normal().dot(p2-p1);
    float u=numerateur/denominateur;
    if (u > -57 && u < 57)
    res=u*(p2-p1)+p1;



    return res;
}

void FaceBSP::separe(const FaceBSP &f) {


    ESign signe=f.sign(_tabVertex[0]->point());
    if(signe!=f.sign(_tabVertex[_tabVertex.size()-1]->point())){
        VertexBSP *inter=createVertex(f.intersection(_tabVertex[_tabVertex.size()-1]->point(),_tabVertex[0]->point()));
        inter->interpolateNormal(*_tabVertex[_tabVertex.size()-1],*_tabVertex[0]);
        vertexNegative.push_back(inter);
        vertexPositive.push_back(inter);
    }
    if(signe==SIGN_PLUS)
        vertexPositive.push_back(_tabVertex[0]);
    else
        vertexNegative.push_back(_tabVertex[0]);
    for (int i = 1; i < _tabVertex.size(); ++i) {
        if(signe!=f.sign(_tabVertex[i]->point())){
            VertexBSP *inter=createVertex(f.intersection(_tabVertex[i-1]->point(),_tabVertex[i]->point()));
                 inter->interpolateNormal(*_tabVertex[i-1],*_tabVertex[i]);
            vertexNegative.push_back(inter);
            vertexPositive.push_back(inter);
        }
        signe=f.sign(_tabVertex[i]->point());
        if(signe==SIGN_PLUS)
            vertexPositive.push_back(_tabVertex[i]);
        else
            vertexNegative.push_back(_tabVertex[i]);
    }

}

ESign FaceBSP::sign(const Vector3 &p) const {

    ESign res=SIGN_NONE;
    if(((_tabVertex[0]->point()-p).dot(_normal))<=0)
        res=SIGN_MINUS;
    else  res=SIGN_PLUS;

    return res;
}


