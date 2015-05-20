attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;

uniform mat4 modelMatrix;
uniform mat4 modelViewMatrix;
uniform mat4 modelViewProjectionMatrix;
uniform mat4 viewProjectionMatrix;
uniform mat4 normalMatrix;

varying vec2 v_texCoord0;
varying vec3 viewSpaceNormal;
varying vec3 viewSpacePosition;

void main() {

    viewSpaceNormal = (normalMatrix * vec4(a_normal, 0.0)).xyz;
    viewSpacePosition = (modelViewMatrix * vec4(a_position, 1.0)).xyz;

    v_texCoord0 = a_texCoord0;
    gl_Position = viewProjectionMatrix * modelMatrix * vec4(a_position, 1.0);
}