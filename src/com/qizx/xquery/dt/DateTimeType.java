/*
 *    Qizx/open 4.1
 *
 * This code is the open-source version of Qizx.
 * Copyright (C) 2004-2009 Axyana Software -- All rights reserved.
 *
 * The contents of this file are subject to the Mozilla Public License 
 *  Version 1.1 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 * The Initial Developer of the Original Code is Xavier Franc - Axyana Software.
 *
 */
package com.qizx.xquery.dt;

import com.qizx.api.EvaluationException;
import com.qizx.api.util.time.DateTime;
import com.qizx.api.util.time.DateTimeException;
import com.qizx.xquery.EvalContext;
import com.qizx.xquery.XQItem;
import com.qizx.xquery.XQItemType;
import com.qizx.xquery.XQType;
import com.qizx.xquery.XQValue;

public class DateTimeType extends MomentType
{
    public String getShortName()
    {
        return "dateTime";
    }

    public int quickCode()
    {
        return QT_DATETIME;
    }

    public XQValue cast(XQItem item, EvalContext context)
        throws EvaluationException
    {
        XQItemType type = item.getItemType();
        DateTime result = null;
        try {
            switch (type.quickCode()) {
            case XQType.QT_STRING:
            case XQType.QT_UNTYPED:
                return SingleMoment.dateTime(item.getDateTime());
            case XQType.QT_DATE:
            case XQType.QT_DATETIME:
                return SingleMoment.dateTime(new DateTime(item.getMoment()));
            case XQType.QT_INT:
            case XQType.QT_DEC:
            case XQType.QT_FLOAT:
            case XQType.QT_DOUBLE: // extension
                if (!context.sObs()) {
                    double s = item.getDouble();
                    return SingleMoment.dateTime(new DateTime((long) (s * 1000)));
                }
                // FALL THROUGH
            default:
                invalidCast(type);
            }
        }
        catch (DateTimeException e) {
            castException(e);
        }
        return new SingleMoment(result, XQType.DATE_TIME);
    }

}
