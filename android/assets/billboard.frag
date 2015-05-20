#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D texture;
varying vec2 v_texCoord0;

void main() {
    //gl_FragColor = vec4(v_texCoord0, 0.0, 1.0);
    gl_FragColor = texture2D(texture, v_texCoord0.yx);
}