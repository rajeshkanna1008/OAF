// To add New Attribute to the EO bases VO 
// To Add new column to the VO using CO Extension
OAApplicationModule am= (OAApplicationModule)pageContext.getApplicationModule(webBean);
OAViewObject vo1=(OAViewObject)am.findViewObject("LeadSearchVO");
pageContext.writeDiagnostics("xxgsts.oracle.apps.asn.lead.webui.ASNLeadLstCOEx.processRequest","In side Process Request",1);
            
	if (vo1 !=null)
	{
	try
		{
			String l_att= vo1.findAttributeDef("XXSUBDATE").toString();
		}
		catch(Exception exception)
		{
			vo1.addDynamicAttribute("XXSUBDATE"); //Adding ViewAttribute to VO
		}
	}
	try
	{
		vo1.reset();
		vo1.next();	
		int i = 0;
		do
		{	
		if (i>0)
		{		
			vo1.next();	
		}
		String SalesLeadId= vo1.getCurrentRow().getAttribute("SalesLeadId").toString();
		String sql2 = "BEGIN xxtcil_lead_det_pkg.getSubmissionDate(:1,:2); END;";
		String submissiondate;
		CallableStatement cs2 = am.getOADBTransaction().createCallableStatement(sql2,OADBTransaction.DEFAULT);
		try
		{
		cs2.registerOutParameter(2, Types.VARCHAR);
		cs2.setString(1, SalesLeadId); 
		cs2.execute(); 
		submissiondate = cs2.getString(2); 
		cs2.close(); 
		} 
		catch (Exception ex) 
		{ 
		throw new OAException("Exception: ", ex.toString()); 
		}
		
		vo1.getCurrentRow().setAttribute("XXSUBDATE",submissiondate);
		//vo1.next();	
		i = i+1;
		
		}
		while (vo1.hasNext());
		vo1.first();
					
		OAMessageStyledTextBean mst=(OAMessageStyledTextBean)webBean.findChildRecursive("XXSUBDATE");
			mst.setViewUsageName("LeadSearchVO");
			mst.setViewAttributeName("XXSUBDATE");
			//mst.setReadOnly(true);
	}
	catch (Exception ex) 
	{ 
		pageContext.writeDiagnostics("xxtcil.oracle.apps.asn.lead.webui.ASNLeadQryCOEx.processformRequest","Catch Block",1);
	}



// If you want to hide or display buttons or fields based on the conditions follow below process

OAApplicationModule am = (OAApplicationModule)paramOAPageContext.getApplicationModule(paramOAWebBean);
String sql = "BEGIN IS_LEAD_OWNER (:1,:2,:3); END;";
        String isOwner;
        Integer userId = paramOAPageContext.getUserId();
        String LeadId = paramOAPageContext.getParameter("ASNReqFrmLeadId");
        CallableStatement cs = am.getOADBTransaction().createCallableStatement(sql, OADBTransaction.DEFAULT);
        try {
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.setString(1, LeadId);
            cs.setInt(2, userId);
            cs.execute();
            isOwner = cs.getString(3);
            cs.close();
        } catch (Exception ex) {
            throw new OAException("Exception: ", ex.toString());
        }

    // Toggle Button Visibility Based on Lead Ownership
        OASubmitButtonBean OptButton = (OASubmitButtonBean)paramOAWebBean.findChildRecursive("ASNPageCnvToOpptyButton");
        if ("N".equals(isOwner)) {
            paramOAPageContext.writeDiagnostics(this, "isOwner Value in If " + isOwner, OAFwkConstants.STATEMENT);
            OptButton.setRendered(false);
        } else {
            paramOAPageContext.writeDiagnostics(this, "isOwner Value in Else " + isOwner, OAFwkConstants.STATEMENT);
            OptButton.setRendered(true);
   
// Different types of buttons
	String sql1 = "BEGIN IS_ADD_PERSON (:1,:2,:3); END;";
        String isPerson;
        CallableStatement cs1 = am.getOADBTransaction().createCallableStatement(sql1, OADBTransaction.DEFAULT);
        try {
            cs1.registerOutParameter(3, Types.VARCHAR);
            cs1.setString(1, LeadId);
            cs1.setInt(2, userId);
            cs1.execute();
            isPerson = cs1.getString(3);
            cs1.close();
        } catch (Exception ex) {
            throw new OAException("Exception: ", ex.toString());
        }

        // Toggle UI Elements Based on Person Assignment
        OAFlowLayoutBean fbl = (OAFlowLayoutBean)paramOAWebBean.findChildRecursive("ASNSTTbAct");
        OAMessageCheckBoxBean checkBoxBean = (OAMessageCheckBoxBean)paramOAWebBean.findChildRecursive("ASNSTOw");
        OAMessageChoiceBean oab = (OAMessageChoiceBean)paramOAWebBean.findChildRecursive("ASNSTGroup");
        OAImageBean oib = (OAImageBean)paramOAWebBean.findChildRecursive("ASNSTRm");
        OASubmitButtonBean OptButton1 = (OASubmitButtonBean)paramOAWebBean.findChildRecursive("ASNPageSvButton");
        OASubmitButtonBean OptButton2 = (OASubmitButtonBean)paramOAWebBean.findChildRecursive("ASNPageFshButton");
        OAImageBean oib2 = (OAImageBean)paramOAWebBean.findChildRecursive("ASNCtctRm");
        //ASNCtctLstTb:ASNCtctRm:0

        if ("N".equals(isPerson)) {
            paramOAPageContext.writeDiagnostics(this, "isPerson Value in If " + isPerson, OAFwkConstants.STATEMENT);
            fbl.setRendered(false);
            checkBoxBean.setReadOnly(true);
            oab.setReadOnly(true);
	   
            oib.setRendered(false);
            OptButton1.setRendered(false);
            OptButton2.setRendered(false);
            oib2.setRendered(false);
           // CLQotRenderUtil.setViewOnlyRecursive(paramOAPageContext, paramOAWebBean);
        } else {
            paramOAPageContext.writeDiagnostics(this, "isPerson Value in Else " + isPerson, OAFwkConstants.STATEMENT);
            fbl.setRendered(true);
            checkBoxBean.setReadOnly(false);
            oab.setReadOnly(false);
            oib.setRendered(true);
            OptButton1.setRendered(true);
            OptButton2.setRendered(true);
            oib2.setRendered(true);
        }

// To set values for the different type of items
String sql6 = "BEGIN xxtcil_lead_add_info_pkg.get_add_info (:1,:2,:3,:4,:5,:6,:7,:8,:9); END;";
		
		String customer_country             ;
        String technology                   ;
        String region                       ;
        String confidence_percentage        ;
        String customer_feedback            ;
        String customer_satisfaction        ;
        String lessons_learned              ;
        String compition_bidder_code_in_inr ;        
		
		CallableStatement cs6 = am.getOADBTransaction().createCallableStatement(sql6, OADBTransaction.DEFAULT);
        try {
            cs6.registerOutParameter(2, Types.VARCHAR);
			cs6.registerOutParameter(3, Types.VARCHAR);
			cs6.registerOutParameter(4, Types.VARCHAR);
			cs6.registerOutParameter(5, Types.VARCHAR);
			cs6.registerOutParameter(6, Types.VARCHAR);
			cs6.registerOutParameter(7, Types.VARCHAR);
			cs6.registerOutParameter(8, Types.VARCHAR);
			cs6.registerOutParameter(9, Types.VARCHAR);
            cs6.setString(1, LeadId);
            cs6.execute();
            customer_country 				= cs6.getString(2);
			technology                  	= cs6.getString(3);
			region                          = cs6.getString(4);
			confidence_percentage           = cs6.getString(5);
			customer_feedback               = cs6.getString(6);
			customer_satisfaction           = cs6.getString(7);
			lessons_learned                 = cs6.getString(8);
			compition_bidder_code_in_inr    = cs6.getString(9);
            cs6.close();
        } catch (Exception ex) {
            throw new OAException("Exception: ", ex.toString());
        }
		
		OAMessageStyledTextBean textInputBean1 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxcountry");
		
		if (textInputBean1 != null) {
            textInputBean1.setValue(paramOAPageContext, customer_country);
        }
		OAMessageStyledTextBean textInputBean2 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxtechnology");
		
		if (textInputBean2 != null) {
            textInputBean2.setValue(paramOAPageContext, technology);
        }
		
		OAMessageStyledTextBean textInputBean3 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxregion");
		if (textInputBean3 != null) {
            textInputBean3.setValue(paramOAPageContext, region);
        }
		OAMessageStyledTextBean textInputBean4 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxconper");
		if (textInputBean4 != null) {
            textInputBean4.setValue(paramOAPageContext, confidence_percentage);
        }
		OAMessageStyledTextBean textInputBean5 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxfeedback");
		if (textInputBean5 != null) {
            textInputBean5.setValue(paramOAPageContext, customer_feedback);
        }
		OAMessageStyledTextBean textInputBean6 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxsatisfaction");
		if (textInputBean6 != null) {
            textInputBean6.setValue(paramOAPageContext, customer_satisfaction);
        }
		OAMessageStyledTextBean textInputBean7 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxlesson");
		if (textInputBean7 != null) {
            textInputBean7.setValue(paramOAPageContext, lessons_learned);
        }
		OAMessageStyledTextBean textInputBean8 = (OAMessageStyledTextBean) paramOAWebBean.findChildRecursive("xxcompition");
		if (textInputBean8 != null) {
            textInputBean8.setValue(paramOAPageContext, compition_bidder_code_in_inr);
        }

// To get custom exceptions based on the validations

String Work_Award_date = paramOAPageContext.getParameter("ASNLeadFlexfield5");
paramOAPageContext.writeDiagnostics(this, "Work_Award_date is " + Work_Award_date, OAFwkConstants.STATEMENT);
    paramOAPageContext.writeDiagnostics(this, "Executing Process Form Request ", OAFwkConstants.STATEMENT);
    String sql1 = "BEGIN XXTCIL_LEAD_DET_PKG.VALIDATE_CLOSE_REASON (:1,:2,:3,:4,:5); END;";
    String Source = "LEAD";
    String Status_check;
    String Msg;
    CallableStatement cs1 = am1.getOADBTransaction().createCallableStatement(sql1, OADBTransaction.DEFAULT);
    try {
        cs1.registerOutParameter(4, Types.VARCHAR);
        cs1.registerOutParameter(5, Types.VARCHAR);
        cs1.setString(1, Status);
        cs1.setString(2, CLOSE_REASON);
        cs1.setString(3, Source);
        cs1.execute();
        Status_check = cs1.getString(4);
        Msg = cs1.getString(5);
        cs1.close();
    } catch (Exception ex) {
        throw new OAException("Exception: ", ex.toString());
    }
    if ("N".equals(Status_check)) {
        throw new OAException(Msg, OAException.ERROR);
    }
	
// To check 2 inputes and validate them

OAApplicationModule am1 = (OAApplicationModule)paramOAPageContext.getApplicationModule(paramOAWebBean);
String Status = paramOAPageContext.getParameter("ASNLeadDetStatus");
String CLOSE_REASON = paramOAPageContext.getParameter("ASNLeadDetRsn");
paramOAPageContext.writeDiagnostics(this, "Status Type is " + Status, OAFwkConstants.STATEMENT);
paramOAPageContext.writeDiagnostics(this, "Close Reason is " + CLOSE_REASON, OAFwkConstants.STATEMENT);
    
	if (paramOAPageContext.getParameter("ASNPageSvButton") != null) 
	{
		String Work_Award_date = paramOAPageContext.getParameter("ASNLeadFlexfield5");
		paramOAPageContext.writeDiagnostics(this, "Work_Award_date is " + Work_Award_date, OAFwkConstants.STATEMENT);
		
		paramOAPageContext.writeDiagnostics(this, "Executing Process Form Request ", OAFwkConstants.STATEMENT);
		String sql1 = "BEGIN XXTCIL_LEAD_DET_PKG.VALIDATE_CLOSE_REASON (:1,:2,:3,:4,:5); END;";
		String Source = "LEAD";
		String Status_check;
		String Msg;
		CallableStatement cs1 = am1.getOADBTransaction().createCallableStatement(sql1, OADBTransaction.DEFAULT);
		try {
			cs1.registerOutParameter(4, Types.VARCHAR);
			cs1.registerOutParameter(5, Types.VARCHAR);
			cs1.setString(1, Status);
			cs1.setString(2, CLOSE_REASON);
			cs1.setString(3, Source);
			cs1.execute();
			Status_check = cs1.getString(4);
			Msg = cs1.getString(5);
			cs1.close();
		} 
		catch (Exception ex) {
			throw new OAException("Exception: ", ex.toString());
		}
		if ("N".equals(Status_check)) {
			throw new OAException(Msg, OAException.ERROR);
		}
	}	

// To make total page readonly
	String OPPTY_ID = paramOAPageContext.getParameter("ASNReqFrmOpptyId");
	Integer userId = paramOAPageContext.getUserId();
	String sql1 = "BEGIN XXTCIL_OPPTY_DET_PKG.is_owner (:1,:2,:3); END;";
	String isPerson;
	CallableStatement cs1 = am1.getOADBTransaction().createCallableStatement(sql1, OADBTransaction.DEFAULT);
		try {
			cs1.registerOutParameter(3, Types.VARCHAR);
			cs1.setString(1, OPPTY_ID);
			cs1.setInt(2, userId);
			cs1.execute();
			isPerson = cs1.getString(3);
			cs1.close();
		} 
		catch (Exception ex) {
			throw new OAException("Exception: ", ex.toString());
		}
	
	OAMessageCheckBoxBean checkBoxBean = (OAMessageCheckBoxBean)paramOAWebBean.findChildRecursive("ASNSTOw");
	OAMessageChoiceBean oab = (OAMessageChoiceBean)paramOAWebBean.findChildRecursive("ASNSTGroup");
	OAImageBean oib = (OAImageBean)paramOAWebBean.findChildRecursive("ASNSTRm");
	OASubmitButtonBean OptButton1 = (OASubmitButtonBean)paramOAWebBean.findChildRecursive("ASNPageSvButton");
	OAImageBean oib2 = (OAImageBean)paramOAWebBean.findChildRecursive("ASNCtctRm");
	paramOAPageContext.writeDiagnostics(this, "isPerson Value in If " + isPerson, OAFwkConstants.STATEMENT);
	
		if ("N".equals(isPerson)) {
			paramOAPageContext.writeDiagnostics(this, "isPerson Value in If " + isPerson, OAFwkConstants.STATEMENT);
			checkBoxBean.setReadOnly(true);
			oab.setReadOnly(true);
			oib.setRendered(false);
			OptButton1.setRendered(false);
			oib2.setRendered(false);
			XXWebBeanUtil.setViewOnlyRecursive(paramOAPageContext, paramOAWebBean); // This line is used to make total page readonly
		} 
		else {
			paramOAPageContext.writeDiagnostics(this, "isPerson Value in Else " + isPerson, OAFwkConstants.STATEMENT);
			checkBoxBean.setReadOnly(false);
			oab.setReadOnly(false);
			oib.setRendered(true);
			OptButton1.setRendered(true);
			oib2.setRendered(true);
		}
