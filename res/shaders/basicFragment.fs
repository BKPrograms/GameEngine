// FRAGMENT SHADER

#version 330

in vec2 texture0;

uniform vec3 colour;
uniform sampler2D sampler;

void main(){
    vec4 textureColour = texture(sampler, texture0.xy);

    if (textureColour == vec4(0,0,0,0)) {
       gl_FragColor = vec4(colour, 1);
    } else {
       gl_FragColor = textureColour * vec4(colour, 1);
    }

}