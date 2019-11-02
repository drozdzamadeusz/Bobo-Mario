#ifdef GL_ES
    precision mediump float;
#endif


uniform sampler2D u_texture; // Texture

uniform sampler2D u_colorTable; // Color table

uniform float u_paletteIndex; // Index that tells the shader which palette to use

varying vec2 v_texCoords;

void main()
{
    vec4 color = texture2D(u_texture, v_texCoords);

    vec2 index = vec2(color.r, u_paletteIndex);

    vec4 indexedColor = texture2D(u_colorTable, index);

    gl_FragColor = vec4(indexedColor.rgb, color.a); // This way we'll preserve alpha
}
