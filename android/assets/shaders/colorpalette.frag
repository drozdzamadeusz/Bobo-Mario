uniform sampler2D texture; // Texture to which we'll apply our shader
uniform sampler2D colorTable; // Color table
uniform float paletteIndex; // Index that tells the shader which palette to use
varying vec2 v_texCoords;

void main()
{
    vec4 color = texture2D(texture, v_texCoords);
    vec2 index = vec2(color.r, paletteIndex);
    vec4 indexedColor = texture2D(colorTable, index);
    gl_FragColor = vec4(indexedColor.rgb, color.a); // This way we'll preserve alpha
}
