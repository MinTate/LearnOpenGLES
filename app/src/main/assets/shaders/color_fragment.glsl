precision mediump float;

uniform sampler2D vTexture;

varying vec2 aCoordinate;

void main(){
    vec4 nColor=texture2D(vTexture, aCoordinate);
    float c=nColor.r*0.299f + nColor.g*0.587f + nColor.b*0.114f;
    gl_FragColor=vec4(c,c,c,nColor.a);
}