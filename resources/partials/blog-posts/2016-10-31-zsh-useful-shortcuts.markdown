# Zsh: useful shortcuts

<table>
  <col width="150"><col width="300"><col width="150"><col width="300">
  <tr>
    <td>
      <b>Shortcut</b>
    </td>
    <td>
      <b>Action</b>
    </td>
    <td>
      <b>Shortcut</b>
    </td>
    <td>
      <b>Action</b>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + E
    </td>
    <td>
      Move to the end of the line
    </td>
    <td>
      CTRL + A
    </td>
    <td>
      Move to the beginning of the line
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_end_start.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + F
    </td>
    <td>
      Move one symbol forward
    </td>
    <td>
      CTRL + B
    </td>
    <td>
      Move one symbol backward
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_forward_backward.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      ALT  + F
    </td>
    <td>
      Move one word forward
    </td>
    <td>
      ALT  + B
    </td>
    <td>
      Move one word backward
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_forward_backward_word.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + U
    </td>
    <td colspan="3">
      Clear the entire line
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_delete_string_undo.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + K
    </td>
    <td colspan="3">
      Clear the characters on the line after the current cursor position
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_delete_string_forward_undo.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      ESC + [backspace]
    </td>
    <td colspan="3">
      Delete the word in front of the cursor
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_delete_word_backward_undo.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + W
    </td>
    <td colspan="3">
      Delete the word in front of the cursor
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_delete_word_forward_undo_2.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      ALT + D
    </td>
    <td colspan="3">
      Delete the word after the cursor
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_delete_word_forward_undo.gif" alt=example>
    </td>
  </tr>
  <tr>
    <td>
      CTRL + _
    </td>
    <td>
      Undo the last change
    </td>
    <td colspan="2">
      See examples above
    </td>
  </tr>
  <tr>
    <td>
      CTRL + R
    </td>
    <td>
      Search history
    </td>
    <td>
      CTRL + G
    </td>
    <td>
      Escape from search mode
    </td>
  </tr>
  <tr>
    <td colspan="4">
      <img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_search_escape.gif" alt=example>
    </td>
  </tr>
</table>
<br>

If uou want to find other useful bindings just run `bindkey|pr -tw132 -4` in your terminal:
<img style="display:block;" width="100%" src="https://s3.amazonaws.com/blog-images.epxlabs.com/9/zsh_bindkey.jpg" alt=bindings>
