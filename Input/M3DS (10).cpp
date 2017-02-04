
ESign FaceBSP::signature(const Vector3 &vector) const {
    ESign result=SIGN_NONE;
    if(((_myVertex[0]->point()-vector).dot(_normal))<=0)
        result=SIGN_MINUS;
    else  result=SIGN_PLUS;

    return result;
}


Vector3 FaceBSP::inta(const Vector3 &vectorIn,const Vector3 &vectorOut) const {
    Vector3 result;

    float num=normal().dot(point(1)-vectorIn);
    float deno=normal().dot(vectorOut-vectorIn);
    float op=num/deno;
    if (op > -57 && op < 57)
    result=op*(vectorOut-vectorIn)+vectorIn;



    return result;
}

void FaceBSP::separate(const FaceBSP &face) {

    ESign eSignObject=face.signature(_myVertex[0]->point());
    if(eSignObject!=face.signature(_myVertex[_myVertex.size()-1]->point())){
        VertexBSP *interVertex=createVertex(face.inta(_myVertex[_myVertex.size()-1]->point(),_myVertex[0]->point()));
        interVertex->interpolateNormal(*_myVertex[_myVertex.size()-1],*_myVertex[0]);
        negatives.push_back(interVertex);
        positives.push_back(interVertex);
    }
    if(eSignObject==SIGN_PLUS)
        positives.push_back(_myVertex[0]);
    else
        negatives.push_back(_myVertex[0]);
    for (int i = 1; i < _myVertex.size(); ++i) {
        if(eSignObject!=face.signature(_myVertex[i]->point())){
            VertexBSP *interVertex=createVertex(face.inta(_myVertex[i-1]->point(),_myVertex[i]->point()));
                 interVertex->interpolateNormal(*_myVertex[i-1],*_myVertex[i]);
            negatives.push_back(interVertex);
            positives.push_back(interVertex);
        }
        eSignObject=face.signature(_myVertex[i]->point());
        if(eSignObject==SIGN_PLUS)
            positives.push_back(_myVertex[i]);
        else
            negatives.push_back(_myVertex[i]);
    }

}
