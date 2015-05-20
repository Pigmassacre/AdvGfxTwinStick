attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 u_projViewTrans;
uniform mat4 u_worldTrans;

varying vec2 v_texCoord0;

void main() {
    v_texCoord0 = a_texCoord0;

    mat4 identityMat = mat4(
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    );

   // vec4 pos = identityMat * vec4(a_position, 1.0);
   // gl_Position = u_worldTrans * pos;
    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);
}