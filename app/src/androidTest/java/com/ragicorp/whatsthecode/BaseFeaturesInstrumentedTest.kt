package com.ragicorp.whatsthecode

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.ragicorp.whatsthecode.library.libContact.stubContact
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val BASIC_SAMPLE_PACKAGE = "com.ragicorp.whatsthecode"
private const val TIMEOUT = 2000L
private const val SMALL_WAIT_TIME = 100L

@RunWith(AndroidJUnit4::class)
class BaseFeaturesInstrumentedTest {
    private lateinit var device: UiDevice

    private lateinit var instrumentationContext: Context

    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertNotNull(launcherPackage)
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            TIMEOUT
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            BASIC_SAMPLE_PACKAGE
        )?.apply {
            // Clear out any previous instances
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
            TIMEOUT
        )

        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    private fun canSeePlaceholder(): Boolean {
        return device.wait(
            Until.findObject(By.text(instrumentationContext.getString(R.string.contactList_noContactPlaceholder))),
            TIMEOUT
        ) != null
    }

    private fun waitTest() {
        Thread.sleep(SMALL_WAIT_TIME)
        device.waitForIdle()
    }

    @Test
    fun baseFeaturesInstrumentedTest() {
        // Start the app and check for placeholder
        assertTrue(canSeePlaceholder())

        // Press the button to add a contact
        device.findObject(By.desc(instrumentationContext.getString(R.string.contactList_addButtonDescription)))
            .click()
        device.wait(
            Until.findObject(By.text(instrumentationContext.getString(R.string.addContact_titleScreen))),
            TIMEOUT
        )

        // Fill a field and back without saving
        var phoneNumberField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_phoneNumber))).parent.parent
        phoneNumberField.text = stubContact.phoneNumber
        waitTest()

        var backButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.modifyContact_leaveButtonDescription)))
        backButton.click()
        waitTest()

        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_title))))
        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_text))))

        val confirmButton =
            device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_confirmButton)))
        confirmButton.click()

        assertTrue(canSeePlaceholder())

        // Can fill all the fields
        device.findObject(By.desc(instrumentationContext.getString(R.string.contactList_addButtonDescription)))
            .click()
        device.wait(
            Until.findObject(By.text(instrumentationContext.getString(R.string.addContact_titleScreen))),
            TIMEOUT
        )

        var saveIcon =
            device.findObject(By.desc(instrumentationContext.getString(R.string.modifyContact_saveButtonDescription))).parent
        waitTest()
        assertFalse(saveIcon.isEnabled)

        phoneNumberField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_phoneNumber))).parent.parent
        phoneNumberField.text = stubContact.phoneNumber
        waitTest()
        assertFalse(saveIcon.isEnabled)

        val apartmentDescriptionField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_apartmentDescription))).parent.parent
        apartmentDescriptionField.text = stubContact.apartmentDescription
        waitTest()
        assertFalse(saveIcon.isEnabled)

        val freeTextField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_freeText))).parent.parent
        freeTextField.text = stubContact.freeText
        waitTest()
        assertFalse(saveIcon.isEnabled)

        val addCodeButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.modifyContact_code_addIconDescription)))
        addCodeButton.click()
        waitTest()
        addCodeButton.click()
        waitTest()

        val editScrollView = device.findObject(By.clazz("android.widget.ScrollView"))
        editScrollView.scroll(Direction.DOWN, 100f)
        waitTest()

        // Trick due to the fact that it sometimes takes time to detect the icon
        var deleteCode3Icon: UiObject2? = null
        while (deleteCode3Icon == null) {
            Thread.sleep(1000L)
            deleteCode3Icon = device.findObject(
                By.desc(
                    instrumentationContext.getString(
                        R.string.modifyContact_code_deleteIconDescription,
                        3
                    )
                )
            )
        }
        deleteCode3Icon.click()
        waitTest()

        for (i in 1..stubContact.codes.size) {
            val doorTextField = device.findObject(
                By.text(
                    instrumentationContext.getString(
                        R.string.modifyContact_code_doorLabel,
                        i
                    )
                )
            ).parent.parent
            doorTextField.text = stubContact.codes[i - 1].first

            val codeTextField = device.findObject(
                By.text(
                    instrumentationContext.getString(
                        R.string.modifyContact_code_codeLabel,
                        i
                    )
                )
            ).parent.parent
            codeTextField.text = stubContact.codes[i - 1].second
        }

        editScrollView.scroll(Direction.UP, 100f)
        waitTest()

        var nameField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_name))).parent.parent
        nameField.text = stubContact.name
        waitTest()
        assertTrue(saveIcon.isEnabled)

        val addressField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_address))).parent.parent
        addressField.text = stubContact.address
        waitTest()
        assertTrue(saveIcon.isEnabled)

        nameField.text = ""
        waitTest()
        assertTrue(saveIcon.isEnabled)

        addressField.text = ""
        waitTest()
        assertFalse(saveIcon.isEnabled)

        nameField.text = stubContact.name
        addressField.text = stubContact.address
        waitTest()
        assertTrue(saveIcon.isEnabled)

        // Try to back to see dialog
        device.pressBack()
        waitTest()

        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_title))))
        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_text))))

        device.pressBack()
        waitTest()
        assertNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_title))))

        // Save and go to contact detail
        saveIcon.click()
        waitTest()

        val wasDeleteIconFound = device.wait(
            Until.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_deleteButtonDescription))),
            TIMEOUT
        ) != null
        assertTrue(wasDeleteIconFound)

        // Can find contact data
        assertNotNull(device.findObject(By.text("JB")))
        assertNotNull(device.findObject(By.text(stubContact.name)))
        assertNotNull(device.findObject(By.text(stubContact.phoneNumber)))
        assertNotNull(device.findObject(By.text(stubContact.address)))

        // Can open dialer app
        val callButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_phoneNumber_phoneButtonDescription)))
        callButton.click()
        waitTest()

        assertNotNull(
            device.wait(
                Until.hasObject(By.pkg("com.google.android.dialer").depth(0)),
                TIMEOUT
            )
        )

        val dialer = device.findObject(By.clazz("android.widget.EditText"))
        assertEquals(stubContact.phoneNumber, dialer.text.replace(" ", ""))

        device.pressBack()
        waitTest()
        device.pressBack()
        waitTest()
        device.pressBack()
        waitTest()

        // Can open sms app
        val smsButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_phoneNumber_sendButtonDescription)))
        smsButton.click()
        waitTest()

        assertNotNull(
            device.wait(
                Until.hasObject(By.pkg("com.google.android.apps.messaging").depth(0)),
                TIMEOUT
            )
        )
        assertNotNull(device.findObject(By.text(stubContact.phoneNumber)))

        device.pressBack()
        waitTest()
        device.pressBack()
        waitTest()

        // Can see other contact data
        val detailScrollView = device.findObject(By.clazz("android.widget.ScrollView"))
        detailScrollView.scroll(Direction.DOWN, 100f)
        waitTest()

        for (code in stubContact.codes) {
            assertNotNull(device.findObject(By.text(code.first)))
            assertNotNull(device.findObject(By.text(code.second)))
        }

        assertNotNull(device.findObject(By.text(stubContact.apartmentDescription)))
        assertNotNull(device.findObject(By.text(stubContact.freeText)))

        // Go back on contact list
        backButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_leaveButtonDescription)))
        backButton.click()
        waitTest()

        val hasFoundAddButton =
            device.findObject(By.desc(instrumentationContext.getString(R.string.contactList_addButtonDescription))) != null
        assertTrue(hasFoundAddButton)

        // No placeholder this time
        assertFalse(canSeePlaceholder())

        // Contact is showing
        assertNotNull(device.findObject(By.text(stubContact.name)))
        assertNotNull(device.findObject(By.text(stubContact.address)))

        // Go to contact edit
        device.findObject(By.text(stubContact.name)).click()
        waitTest()
        device.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_eidtButtonDescription)))
            .click()
        waitTest()

        saveIcon =
            device.findObject(By.desc(instrumentationContext.getString(R.string.modifyContact_saveButtonDescription))).parent
        waitTest()
        assertFalse(saveIcon.isEnabled)

        // Modify something and try to back
        nameField =
            device.findObject(By.text(instrumentationContext.getString(R.string.contact_name))).parent.parent
        val newName = "Emmanuel Macron"
        nameField.text = newName
        waitTest()
        assertTrue(saveIcon.isEnabled)

        device.pressBack()
        waitTest()
        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_title))))
        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.unsavedChangesDialog_text))))
        device.pressBack()

        // Save and see changes
        saveIcon.click()
        waitTest()

        assertNull(device.findObject(By.text(stubContact.name)))
        assertNotNull(device.findObject(By.text(newName)))
        assertNotNull(device.findObject(By.text("EM")))

        val deleteIcon =
            device.findObject(By.desc(instrumentationContext.getString(R.string.contactDetail_deleteButtonDescription)))
        deleteIcon.click()
        waitTest()

        assertNotNull(device.findObject(By.text(instrumentationContext.getString(R.string.deleteContactDialog_title))))
        device.findObject(By.text(instrumentationContext.getString(R.string.deleteContactDialog_confirmButton)))
            .click()
        waitTest()

        assertTrue(canSeePlaceholder())
    }
}