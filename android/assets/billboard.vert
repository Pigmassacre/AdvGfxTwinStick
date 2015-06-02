attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 modelMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 modelViewProjectionMatrix;
uniform mat4 projectionMatrix;
uniform mat4 normalMatrix;

varying vec2 v_texCoord0;
varying vec3 viewSpaceNormal;
varying vec3 viewSpacePosition;

void main() {

    viewSpaceNormal = (normalMatrix * vec4(a_normal, 0.0)).xyz;
    viewSpacePosition = (modelViewMatrix * vec4(a_position, 1.0)).xyz;

    mat4 modelViewNew = modelViewMatrix;

    // Column 0
    modelViewNew[0][0] = 1;
    modelViewNew[0][1] = 0;
    modelViewNew[0][2] = 0;

    // Column 1, comment this out for cylindrical billboarding
    modelViewNew[1][0] = 0;
    modelViewNew[1][1] = 1;
    modelViewNew[1][2] = 0;

    // Column 2
    modelViewNew[2][0] = 0;
    modelViewNew[2][1] = 0;
    modelViewNew[2][2] = 1;

    v_texCoord0 = vec2(a_texCoord0.y, a_texCoord0.x);
    gl_Position = projectionMatrix * modelViewNew * vec4(a_position, 1.0);
}