package cn.way.sample.material_dialog;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import cn.way.material_dialogs.DialogAction;
import cn.way.material_dialogs.GravityEnum;
import cn.way.material_dialogs.MaterialDialog;
import cn.way.material_dialogs.StackingBehavior;
import cn.way.material_dialogs.Theme;
import cn.way.material_dialogs.color.CircleView;
import cn.way.material_dialogs.color.ColorChooserDialog;
import cn.way.material_dialogs.folderselector.FileChooserDialog;
import cn.way.material_dialogs.folderselector.FolderChooserDialog;
import cn.way.material_dialogs.internal.MDTintHelper;
import cn.way.material_dialogs.internal.ThemeSingleton;
import cn.way.material_dialogs.simplelist.MaterialSimpleListAdapter;
import cn.way.material_dialogs.simplelist.MaterialSimpleListItem;
import cn.way.material_dialogs.util.DialogUtils;
import cn.way.sample.R;
import cn.way.sample.util.AppUtils;


public class MaterialDialogActivity extends AppCompatActivity
        implements FolderChooserDialog.FolderCallback,
        FileChooserDialog.FileCallback,
        ColorChooserDialog.ColorCallback {

    private static final int STORAGE_PERMISSION_RC = 69;
    static int index = 0;
    // Custom View Dialog
    private EditText passwordInput;
    private View positiveAction;
    // color chooser dialog
    private int primaryPreselect;
    // UTILITY METHODS
    private int accentPreselect;
    private Toast toast;
    private Thread thread;
    private Handler handler;
    private int chooserDialog;

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void startThread(Runnable run) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(run);
        thread.start();
    }

    // BEGIN SAMPLE

    private void showToast(@StringRes int message) {
        showToast(getString(message));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_dialog);

        handler = new Handler();
        primaryPreselect = DialogUtils.resolveColor(this, R.attr.colorPrimary);
        accentPreselect = DialogUtils.resolveColor(this, R.attr.colorAccent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (thread != null && !thread.isInterrupted() && thread.isAlive()) {
            thread.interrupt();
        }
    }

    public void showBasicNoTitle(View view) {
        new MaterialDialog.Builder(this)
                .content(R.string.shareLocationPrompt)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .show();
    }

    public void showBasic(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .show();
    }

    public void showBasicLongContent(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.loremIpsum)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .checkBoxPrompt("Hello, world!", true, null)
                .show();
    }

    public void showBasicIcon(View view) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .show();
    }

    public void showBasicCheckPrompt(View view) {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize()
                .title(Html.fromHtml(getString(R.string.permissionSample, getString(R.string.app_name))))
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onAny((dialog, which) -> showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked()))
                .checkBoxPromptRes(R.string.dont_ask_again, false, null)
                .show();
    }

    public void showStacked(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.speedBoost)
                .negativeText(R.string.noThanks)
                .btnStackedGravity(GravityEnum.END)
                .stackingBehavior(
                        StackingBehavior
                                .ALWAYS) // this generally should not be forced, but is used for demo purposes
                .show();
    }

    public void showNeutral(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .show();
    }

    public void showCallbacks(View view) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .onAny((dialog, which) -> showToast(which.name() + "!"))
                .show();
    }

    public void showList(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallback((dialog, view, which, text) -> showToast(which + ": " + text))
                .show();
    }

    public void showListNoTitle(View v) {
        new MaterialDialog.Builder(this)
                .items(R.array.socialNetworks)
                .itemsCallback((dialog, view, which, text) -> showToast(which + ": " + text))
                .show();
    }

    public void showLongList(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.states)
                .items(R.array.states)
                .itemsCallback((dialog, view, which, text) -> showToast(which + ": " + text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    public void showListLongItems(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallback((dialog, view, which, text) -> showToast(which + ": " + text))
                .show();
    }

    public void showListCheckPrompt(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.states)
                .itemsCallback(
                        (dialog, view, which, text) ->
                                showToast(which + ": " + text + ", prompt: " + dialog.isPromptCheckBoxChecked()))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .negativeText(android.R.string.cancel)
                .show();
    }

    @SuppressWarnings("ConstantConditions")
    public void showListLongPress(View v) {
        index = 0;
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallback((dialog, view, which, text) -> showToast(which + ": " + text))
                .autoDismiss(false)
                .itemsLongCallback(
                        (dialog, itemView, position, text) -> {
                            dialog.getItems().remove(position);
                            dialog.notifyItemsChanged();
                            return false;
                        })
                .onNeutral(
                        (dialog, which) -> {
                            index++;
                            dialog.getItems().add("Item " + index);
                            dialog.notifyItemInserted(dialog.getItems().size() - 1);
                        })
                .neutralText(R.string.add_item)
                .show();
    }

    public void showSingleChoice(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsDisabledIndices(1, 3)
                .itemsCallbackSingleChoice(
                        2,
                        (dialog, view, which, text) -> {
                            showToast(which + ": " + text);
                            return true; // allow selection
                        })
                .positiveText(R.string.md_choose_label)
                .show();
    }

    public void showSingleChoiceLongItems(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallbackSingleChoice(
                        2,
                        (dialog, view, which, text) -> {
                            showToast(which + ": " + text);
                            return true; // allow selection
                        })
                .positiveText(R.string.md_choose_label)
                .show();
    }

    public void showMultiChoice(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(
                        new Integer[]{1, 3},
                        (dialog, which, text) -> {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < which.length; i++) {
                                if (i > 0) {
                                    str.append('\n');
                                }
                                str.append(which[i]);
                                str.append(": ");
                                str.append(text[i]);
                            }
                            showToast(str.toString());
                            return true; // allow selection
                        })
                .onNeutral((dialog, which) -> dialog.clearSelectedIndices())
                .onPositive((dialog, which) -> dialog.dismiss())
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText(R.string.clear_selection)
                .show();
    }

    public void showMultiChoiceLimited(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(
                        new Integer[]{1},
                        (dialog, which, text) -> {
                            boolean allowSelectionChange =
                                    which.length
                                            <= 2; // limit selection to 2, the new (un)selection is included in the which
                            // array
                            if (!allowSelectionChange) {
                                showToast(R.string.selection_limit_reached);
                            }
                            return allowSelectionChange;
                        })
                .positiveText(R.string.dismiss)
                .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if
                // (un)selection is still allowed
                .show();
    }

    public void showMultiChoiceLimitedMin(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(
                        new Integer[]{1},
                        (dialog, which, text) -> {
                            boolean allowSelectionChange =
                                    which.length
                                            >= 1; // selection count must stay above 1, the new (un)selection is included
                            // in the which array
                            if (!allowSelectionChange) {
                                showToast(R.string.selection_min_limit_reached);
                            }
                            return allowSelectionChange;
                        })
                .positiveText(R.string.dismiss)
                .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if
                // (un)selection is still allowed
                .show();
    }

    public void showMultiChoiceLongItems(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks_longItems)
                .itemsCallbackMultiChoice(
                        new Integer[]{1, 3},
                        (dialog, which, text) -> {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < which.length; i++) {
                                if (i > 0) {
                                    str.append('\n');
                                }
                                str.append(which[i]);
                                str.append(": ");
                                str.append(text[i]);
                            }
                            showToast(str.toString());
                            return true; // allow selection
                        })
                .positiveText(R.string.md_choose_label)
                .show();
    }

    public void showMultiChoiceDisabledItems(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.socialNetworks)
                .items(R.array.socialNetworks)
                .itemsCallbackMultiChoice(
                        new Integer[]{0, 1, 2},
                        (dialog, which, text) -> {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < which.length; i++) {
                                if (i > 0) {
                                    str.append('\n');
                                }
                                str.append(which[i]);
                                str.append(": ");
                                str.append(text[i]);
                            }
                            showToast(str.toString());
                            return true; // allow selection
                        })
                .onNeutral((dialog, which) -> dialog.clearSelectedIndices())
                .alwaysCallMultiChoiceCallback()
                .positiveText(R.string.md_choose_label)
                .autoDismiss(false)
                .neutralText(R.string.clear_selection)
                .itemsDisabledIndices(0, 1)
                .show();
    }

    public void showSimpleList(View v) {
        final MaterialSimpleListAdapter adapter =
                new MaterialSimpleListAdapter(
                        (dialog, index1, item) -> showToast(item.getContent().toString()));
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("username@gmail.com")
                        .icon(R.drawable.ic_account_circle)
                        .backgroundColor(Color.WHITE)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content("user02@gmail.com")
                        .icon(R.drawable.ic_account_circle)
                        .backgroundColor(Color.WHITE)
                        .build());
        adapter.add(
                new MaterialSimpleListItem.Builder(this)
                        .content(R.string.add_account)
                        .icon(R.drawable.ic_content_add)
                        .iconPaddingDp(8)
                        .build());

        new MaterialDialog.Builder(this).title(R.string.set_backup).adapter(adapter, null).show();
    }

    public void showCustomList(View v) {
        final ButtonItemAdapter adapter = new ButtonItemAdapter(this, R.array.socialNetworks);
        adapter.setCallbacks(
                itemIndex -> showToast("Item clicked: " + itemIndex),
                buttonIndex -> showToast("Button clicked: " + buttonIndex));
        new MaterialDialog.Builder(this).title(R.string.socialNetworks).adapter(adapter, null).show();
    }

    @SuppressWarnings("ResourceAsColor")
    public void showCustomView(View v) {
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title(R.string.googleWifi)
                        .customView(R.layout.dialog_customview, true)
                        .positiveText(R.string.connect)
                        .negativeText(android.R.string.cancel)
                        .onPositive(
                                (dialog1, which) -> showToast("Password: " + passwordInput.getText().toString()))
                        .build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

        // Toggling the show password CheckBox will mask or unmask the password input EditText
        CheckBox checkbox = dialog.getCustomView().findViewById(R.id.showPassword);
        checkbox.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
                    passwordInput.setInputType(
                            !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                    passwordInput.setTransformationMethod(
                            !isChecked ? PasswordTransformationMethod.getInstance() : null);
                });

        int widgetColor = ThemeSingleton.get().widgetColor;
        MDTintHelper.setTint(
                checkbox, widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

        MDTintHelper.setTint(
                passwordInput,
                widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    public void showCustomWebView(View v) {
        int accentColor = ThemeSingleton.get().widgetColor;
        if (accentColor == 0) {
            accentColor = ContextCompat.getColor(this, R.color.accent);
        }
        ChangelogDialog.create(false, accentColor).show(getSupportFragmentManager(), "changelog");
    }

    public void showCustomDatePicker(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.date_picker)
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .show();
    }

    public void showColorChooserPrimary(View v) {
        new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                .preselect(primaryPreselect)
                .show();
    }

    public void showColorChooserAccent(View view) {
        new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                .accentMode(true)
                .preselect(accentPreselect)
                .show();
    }

    public void showColorChooserCustomColors(View v) {
        int[][] subColors =
                new int[][]{
                        new int[]{
                                Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935")
                        },
                        new int[]{
                                Color.parseColor("#EC407A"), Color.parseColor("#E91E63"), Color.parseColor("#D81B60")
                        },
                        new int[]{
                                Color.parseColor("#AB47BC"), Color.parseColor("#9C27B0"), Color.parseColor("#8E24AA")
                        },
                        new int[]{
                                Color.parseColor("#7E57C2"), Color.parseColor("#673AB7"), Color.parseColor("#5E35B1")
                        },
                        new int[]{
                                Color.parseColor("#5C6BC0"), Color.parseColor("#3F51B5"), Color.parseColor("#3949AB")
                        },
                        new int[]{
                                Color.parseColor("#42A5F5"), Color.parseColor("#2196F3"), Color.parseColor("#1E88E5")
                        }
                };

        new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                .preselect(primaryPreselect)
                .customColors(R.array.custom_colors, subColors)
                .show();
    }

    public void showColorChooserCustomColorsNoSub(View v) {
        new ColorChooserDialog.Builder(this, R.string.color_palette)
                .titleSub(R.string.colors)
                .preselect(primaryPreselect)
                .customColors(R.array.custom_colors, null)
                .show();
    }

    // Receives callback from color chooser dialog
    @Override
    public void onColorSelection(ColorChooserDialog dialog, @ColorInt int color) {
        if (dialog.isAccentMode()) {
            accentPreselect = color;
            ThemeSingleton.get().positiveColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().neutralColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().negativeColor = DialogUtils.getActionTextStateList(this, color);
            ThemeSingleton.get().widgetColor = color;
        } else {
            primaryPreselect = color;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
                getWindow().setNavigationBarColor(color);
            }
        }
    }

    @Override
    public void onColorChooserDismissed(ColorChooserDialog dialog) {
        showToast("Color chooser dismissed!");
    }

    public void showThemed(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .positiveColorRes(R.color.material_red_400)
                .negativeColorRes(R.color.material_red_400)
                .titleGravity(GravityEnum.CENTER)
                .titleColorRes(R.color.material_red_400)
                .contentColorRes(android.R.color.white)
                .backgroundColorRes(R.color.material_blue_grey_800)
                .dividerColorRes(R.color.accent)
                .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE)
                .positiveColor(Color.WHITE)
                .negativeColorAttr(android.R.attr.textColorSecondaryInverse)
                .theme(Theme.DARK)
                .show();
    }

    public void showShowCancelDismissCallbacks(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.useGoogleLocationServices)
                .content(R.string.useGoogleLocationServicesPrompt, true)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .neutralText(R.string.more_info)
                .showListener(dialog -> showToast("onShow"))
                .cancelListener(dialog -> showToast("onCancel"))
                .dismissListener(dialog -> showToast("onDismiss"))
                .show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showFileChooser(View v) {
        chooserDialog = R.id.file_chooser;
        if (ActivityCompat.checkSelfPermission(
                MaterialDialogActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MaterialDialogActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_RC);
            return;
        }
        new FileChooserDialog.Builder(this).show();
    }

    @Override
    public void onFileSelection(FileChooserDialog dialog, File file) {
        showToast(file.getAbsolutePath());
    }

    @Override
    public void onFileChooserDismissed(FileChooserDialog dialog) {
        showToast("File chooser dismissed!");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showFolderChooser(View v) {
        chooserDialog = R.id.folder_chooser;
        if (ActivityCompat.checkSelfPermission(
                MaterialDialogActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MaterialDialogActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_RC);
            return;
        }
        new FolderChooserDialog.Builder(MaterialDialogActivity.this)
                .chooseButton(R.string.md_choose_label)
                .allowNewFolder(true, 0)
                .show();
    }

    @Override
    public void onFolderSelection(FolderChooserDialog dialog, File folder) {
        showToast(folder.getAbsolutePath());
    }

    @Override
    public void onFolderChooserDismissed(FolderChooserDialog dialog) {
        showToast("Folder chooser dismissed!");
    }

    public void showInputDialog(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.input)
                .content(R.string.input_content)
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 16)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_hint,
                        false,
                        (dialog, input) -> showToast("Hello, " + input.toString() + "!"))
                .show();
    }

    public void showInputDialogCustomInvalidation(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.input)
                .content(R.string.input_content_custominvalidation)
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(R.string.submit)
                .alwaysCallInputCallback() // this forces the callback to be invoked with every input change
                .input(
                        R.string.input_hint,
                        0,
                        false,
                        (dialog, input) -> {
                            if (input.toString().equalsIgnoreCase("hello")) {
                                dialog.setContent("I told you not to type that!");
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            } else {
                                dialog.setContent(R.string.input_content_custominvalidation);
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            }
                        })
                .show();
    }

    public void showInputDialogCheckPrompt(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.input)
                .content(R.string.input_content)
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(2, 16)
                .positiveText(R.string.submit)
                .input(
                        R.string.input_hint,
                        R.string.input_hint,
                        false,
                        (dialog, input) -> showToast("Hello, " + input.toString() + "!"))
                .checkBoxPromptRes(R.string.example_prompt, true, null)
                .show();
    }

    public void showProgressDeterminateDialog(View v) {
        new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 150, true)
                .cancelListener(
                        dialog -> {
                            if (thread != null) {
                                thread.interrupt();
                            }
                        })
                .showListener(
                        dialogInterface -> {
                            final MaterialDialog dialog = (MaterialDialog) dialogInterface;
                            startThread(
                                    () -> {
                                        while (dialog.getCurrentProgress() != dialog.getMaxProgress()
                                                && !Thread.currentThread().isInterrupted()) {
                                            if (dialog.isCancelled()) {
                                                break;
                                            }
                                            try {
                                                Thread.sleep(50);
                                            } catch (InterruptedException e) {
                                                break;
                                            }
                                            dialog.incrementProgress(1);
                                        }
                                        runOnUiThread(
                                                () -> {
                                                    thread = null;
                                                    dialog.setContent(getString(R.string.md_done_label));
                                                });
                                    });
                        })
                .show();
    }

    public void showProgressIndeterminateDialog(View v) {
        showIndeterminateProgressDialog(false);
    }

    public void showProgressHorizontalIndeterminateDialog(View v) {
        showIndeterminateProgressDialog(true);
    }

    private void showIndeterminateProgressDialog(boolean horizontal) {
        new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .show();
    }

    public void showPreferenceDialogs(View v) {
        AppUtils.launch(this, PreferenceActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_material_dialog, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            AboutDialog.show(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_RC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handler.postDelayed(() -> findViewById(chooserDialog).performClick(), 1000);
            } else {
                Toast.makeText(
                        this,
                        "The folder or file chooser will not work without "
                                + "permission to read external storage.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
